@echo off
git add src/ -A
set /p commitMessage= Commit message:
git commit -m "%commitMessage%"
git push origin master
echo Press Enter...
read