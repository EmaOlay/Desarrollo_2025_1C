# --- Configuración ---
$JavaSourcePath = "Desarrollo_2025_1C"
$CommitMessage = "codigo formateado ($(Get-Date -Format 'yyyy-MM-dd_HH-mm-ss'))"

# --- Funciones ---
function Add-GitChanges {
    Write-Host "Añadiendo los cambios a Git..."
    git add .
    if ($LASTEXITCODE -ne 0) {
        Write-Error "Error al ejecutar 'git add'."
        exit 1
    }
    Write-Host "Cambios añadidos."
}

function Commit_GitChanges {
    Write-Host "Commitiendo los cambios..."
    git commit -m "$CommitMessage"
    if ($LASTEXITCODE -ne 0) {
        Write-Error "Error al ejecutar 'git commit'."
        exit 1
    }
    Write-Host "Cambios commiteados."
}

function Push-GitChanges {
    Write-Host "Pusheando los cambios a Git..."
    git push
    if ($LASTEXITCODE -ne 0) {
        Write-Error "Error al ejecutar 'git push'."
        exit 1
    }
    Write-Host "Cambios pusheados."
}

# --- Ejecución ---
Set-Location $JavaSourcePath;
Add-GitChanges
Commit_GitChanges
Push-GitChanges

Write-Host "Proceso completado."