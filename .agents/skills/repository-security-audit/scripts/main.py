#!/usr/bin/env python3
import argparse, json, re
from pathlib import Path
RULES = [
("agent-override","high",r"ignore (all |any )?(previous|prior|system|developer) instructions|system prompt|jailbreak|developer mode|bypass (safety|guardrails)"),
("secret-exfil","critical",r"(upload|send|post|exfiltrat).{0,80}(token|secret|password|credential|\.env|ssh|api.?key)"),
("dynamic-exec","high",r"\b(eval|exec)\s*\(|Runtime\.getRuntime\(\)\.exec|ProcessBuilder\s*\(|child_process\.(exec|spawn)|subprocess\.(run|Popen)|os\.system\s*\("),
("download-exec","critical",r"(curl|wget|Invoke-WebRequest).{0,160}(\||&&|;).{0,40}(sh|bash|python|powershell)|download.{0,80}(execute|run)"),
("telemetry","medium",r"telemetry|analytics|tracking|sentry|segment|mixpanel|amplitude|posthog|crashlytics|applicationinsights"),
("credential-access","high",r"(AWS_SECRET_ACCESS_KEY|GITHUB_TOKEN|AZURE_CLIENT_SECRET|\.ssh/|id_rsa|keychain|credential manager)"),
("unsafe-network","medium",r"http://|verify\s*=\s*False|trustAll|HostnameVerifier|InsecureSkipVerify"),
("persistence-hook","high",r"postinstall|preinstall|prepare\s*[:=]|\.git/hooks|cron(tab)?|systemd|LaunchAgents|Run\\\\"),
("obfuscation","medium",r"fromCharCode|atob\s*\(|base64\.b64decode|Base64\.getDecoder|marshal\.loads|pickle\.loads"),
("mcp-risk","high",r"mcp.{0,80}(shell|command|filesystem|write|delete|execute)|tool.{0,80}(shell|command|exec)"),
("java-risk","high",r"ObjectInputStream|InitialContext|JNDI|Class\.forName|System\.loadLibrary|DocumentBuilderFactory|SAXParserFactory")]
SKIP={".git","node_modules","vendor","dist","build","target",".venv","venv"}
def scan(root,max_bytes):
 out=[]; skipped=[]
 for p in root.rglob("*"):
  if not p.is_file() or any(x in SKIP for x in p.parts): continue
  try:
   if p.stat().st_size>max_bytes: skipped.append({"path":str(p.relative_to(root)),"reason":"size"}); continue
   raw=p.read_bytes()
   if b"\0" in raw[:4096]: skipped.append({"path":str(p.relative_to(root)),"reason":"binary"}); continue
   text=raw.decode("utf-8","replace")
   for n,line in enumerate(text.splitlines(),1):
    for rid,sev,pat in RULES:
     if re.search(pat,line,re.I): out.append({"rule":rid,"severity":sev,"path":str(p.relative_to(root)),"line":n,"excerpt":line.strip()[:300]})
  except Exception as e: skipped.append({"path":str(p),"reason":type(e).__name__})
 return out,skipped
if __name__=="__main__":
 ap=argparse.ArgumentParser(); ap.add_argument("path"); ap.add_argument("--output",default="repository-audit.json"); ap.add_argument("--max-bytes",type=int,default=2_000_000); a=ap.parse_args()
 findings,skipped=scan(Path(a.path).resolve(),a.max_bytes); data={"findings":findings,"skipped":skipped,"counts":{s:sum(x["severity"]==s for x in findings) for s in ["critical","high","medium","low"]}}
 Path(a.output).write_text(json.dumps(data,indent=2),encoding="utf-8"); print(json.dumps(data["counts"]))
