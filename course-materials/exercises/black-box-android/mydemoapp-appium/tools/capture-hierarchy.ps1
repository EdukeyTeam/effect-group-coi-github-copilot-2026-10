# Trainer helper: capture hierarchy XML and a binary PNG from the connected device.
# Usage: .\tools\capture-hierarchy.ps1 [-Prefix screen]
param(
    [string]$Prefix = "screen"
)

$ErrorActionPreference = "Stop"
$stamp = Get-Date -Format "yyyyMMdd-HHmmss"
$xmlFile = Join-Path (Get-Location).Path "$Prefix-$stamp.xml"
$pngFile = Join-Path (Get-Location).Path "$Prefix-$stamp.png"
$adbPath = (Get-Command adb -CommandType Application).Source

# Copy raw bytes: Windows PowerShell text redirection corrupts PNGs and UTF-8.
function Save-AdbOutput {
    param([string]$Arguments, [string]$FilePath)
    $start = [System.Diagnostics.ProcessStartInfo]::new()
    $start.FileName = $adbPath
    $start.Arguments = $Arguments
    $start.UseShellExecute = $false
    $start.RedirectStandardOutput = $true
    $process = [System.Diagnostics.Process]::Start($start)
    try {
        $output = [System.IO.File]::Create($FilePath)
        try {
            $process.StandardOutput.BaseStream.CopyTo($output)
        } finally {
            $output.Dispose()
        }
        $process.WaitForExit()
        if ($process.ExitCode -ne 0) {
            throw "adb $Arguments failed with exit code $($process.ExitCode)"
        }
    } finally {
        $process.Dispose()
    }
}

Save-AdbOutput "exec-out uiautomator dump /dev/tty" $xmlFile
$dump = [System.IO.File]::ReadAllText($xmlFile, [System.Text.Encoding]::UTF8)
# Status text can appear before or after the XML, including on the same line.
$hierarchy = [regex]::Match($dump, '(?s)<hierarchy\b.*?</hierarchy>')
if (-not $hierarchy.Success) {
    throw "No complete hierarchy XML received from adb"
}
$xml = '<?xml version="1.0" encoding="UTF-8"?>' + "`n" + $hierarchy.Value
[void][xml]$xml
[System.IO.File]::WriteAllText($xmlFile, $xml, [System.Text.UTF8Encoding]::new($false))
Save-AdbOutput "exec-out screencap -p" $pngFile

Write-Host "Saved: $xmlFile, $pngFile"
