# Makefile para un Desarrollo
SQL_DIR:='Ing de datos/sql'
SQL_FORMATTER:='sql-formatter'
# Comandos
GIT := git


# Formateo de código SQL
format-sql:
	@echo "Formateando el código SQL con SQL Formatter..."
	find $(SQL_DIR) -name '*.sql' -exec $(SQL_FORMATTER) {} \; # Formatea los archivos SQL encontrados
	@echo "Formato de SQL completado."

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