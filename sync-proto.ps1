$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$source = Join-Path $root "proto"
$targets = @(
    "api-gateway/src/main/proto",
    "job-service/src/main/proto",
    "worker-service/src/main/proto"
)

foreach ($target in $targets) {
    $targetPath = Join-Path $root $target
    New-Item -ItemType Directory -Force -Path $targetPath | Out-Null
    Copy-Item -Path (Join-Path $source "*.proto") -Destination $targetPath -Force
    Write-Output "Synced proto files to $target"
}
