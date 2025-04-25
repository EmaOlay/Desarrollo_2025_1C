# Makefile para un Desarrollo
# Comandos
GIT := git

# Comandos de Git
git:
	@echo "Ejecutando comandos de Git (add, commit, push)...\n"
	@echo "Primero, agrega los archivos modificados (git add .)..."
	$(GIT) add .
	@echo "Luego, haz el commit (git commit -m \"Cambios\")..."
	@echo "Por favor, ingresa el mensaje del commit:"
	read -r COMMIT_MESSAGE; \
	$(GIT) commit -m "$$COMMIT_MESSAGE"
	@echo "Finalmente, haz el push (git push)..."
	$(GIT) push
	@echo "Comandos de Git completados."

# Todo en uno
all: git