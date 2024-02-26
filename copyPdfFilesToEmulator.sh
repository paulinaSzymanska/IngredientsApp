#!/bin/bash

# Ścieżka do folderu z plikami PDF
source_dir="recipes"

# Sprawdzenie czy folder docelowy na telefonie istnieje
if [ ! -d "/mnt/phone/Recipes" ]; then
    mkdir /mnt/phone/Recipes
fi

# Kopiowanie plików PDF do folderu na telefonie
cp "$source_dir"/*.pdf /mnt/phone/Recipes

# Sprawdzenie, czy kopiowanie się powiodło
if [ $? -eq 0 ]; then
    echo "Pliki PDF zostały pomyślnie skopiowane na telefon."
else
    echo "Wystąpił błąd podczas kopiowania plików PDF na telefon."
fi
