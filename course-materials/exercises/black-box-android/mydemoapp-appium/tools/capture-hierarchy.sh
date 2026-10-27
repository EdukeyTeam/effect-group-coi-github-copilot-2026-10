#!/usr/bin/env bash
# Trainer helper: dump the current screen's UI hierarchy and a screenshot
# from the connected device. Files land in the current directory with a timestamp.
#
# Usage: ./tools/capture-hierarchy.sh [prefix]
#   prefix - optional file name prefix, default "screen"
set -euo pipefail

prefix="${1:-screen}"
stamp="$(date +%Y%m%d-%H%M%S)"
xml_file="${prefix}-${stamp}.xml"
png_file="${prefix}-${stamp}.png"

# Status text may precede or follow the XML, even on the same line.
dump="$(adb exec-out uiautomator dump /dev/tty)"
if [[ "$dump" != *"<hierarchy"*"</hierarchy>"* ]]; then
    echo "No complete hierarchy XML received from adb" >&2
    exit 1
fi
hierarchy="${dump#*<hierarchy}"
hierarchy="${hierarchy%%</hierarchy>*}"
printf '<?xml version="1.0" encoding="UTF-8"?>\n<hierarchy%s</hierarchy>\n' "$hierarchy" > "$xml_file"
adb exec-out screencap -p > "$png_file"

echo "Saved: $xml_file, $png_file"
