#!/bin/bash
# Salva il contenuto del repository in git
#unset DISPLAY
unset SSH_ASKPASS

#echo "Aggiorno la documentazione online"
#./grailsw doc
echo "git add ."
git add .
echo "git commit -a -m \"$1\""
git commit -a -m "$1"
echo "git push"
git push -f origin origin:master
echo "aggiorna documentazione web su GitHub"
git push -f origin origin:gh-pages

exit 0
