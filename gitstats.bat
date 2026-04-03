@echo off
setlocal DisableDelayedExpansion

:: We use a single line for the PS_CODE to avoid CMD's temperamental line-break handling
set "PS_CODE=$map = @{'jasper07.verbruggen@gmail.com'='jasper.verbruggen@student.kdg.be';'thomas.wellens.1@kdg.student.be'='thomas.wellens.1@student.kdg.be'; 'cin.work01@gmail.com'='yari.deschepper@student.kdg.be'}; $stats = @{}; $seenAuthorFiles = @{}; $log = git log %* --format='author: %%ae' --numstat; foreach ($line in $log) { if ($line -match '^author: (.*)') { $email = $matches[1].ToLower().Trim(); if ($map.ContainsKey($email)) { $email = $map[$email] } $currentAuthor = $email; if (-not $stats.ContainsKey($currentAuthor)) { $stats[$currentAuthor] = [PSCustomObject]@{Email=$currentAuthor; Commits=0; Files=0; Insertions=0; Deletions=0; Total=0} } $stats[$currentAuthor].Commits++ } elseif ($line -match '^(\d+)\s+(\d+)\s+(.*)') { $ins = [int]$matches[1]; $del = [int]$matches[2]; $file = $matches[3]; $key = \"${currentAuthor}:${file}\"; if (-not $seenAuthorFiles.ContainsKey($key)) { $seenAuthorFiles[$key] = $true; $stats[$currentAuthor].Files++ } $stats[$currentAuthor].Insertions += $ins; $stats[$currentAuthor].Deletions += $del; $stats[$currentAuthor].Total += ($ins + $del) } }; $stats.Values | Sort-Object Total -Descending | Format-Table -AutoSize"

powershell -NoProfile -ExecutionPolicy Bypass -Command "%PS_CODE%"
