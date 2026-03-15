# GitHub Setup & First Push Guide

This guide walks you through pushing your Fantasy Football project to GitHub.

---

## Step 1: Create GitHub Repository

### 1.1 - Go to GitHub
- Navigate to [GitHub](https://github.com) and log in (create account if needed)
- Click **"+"** button in top-right corner
- Select **"New repository"**

### 1.2 - Configure Repository

| Field | Value |
|-------|-------|
| **Repository name** | `fantasy-football` |
| **Description** | `Zero-cost fantasy football platform for European soccer leagues. Built with Spring Boot 3, clean architecture, and best practices.` |
| **Visibility** | Public (or Private if you prefer) |
| **Initialize repository** | ❌ Do NOT check "Add README.md" (we have one) |
| **Add .gitignore** | ❌ Do NOT add (we have one) |
| **License** | MIT License |

### 1.3 - Create Repository
Click **"Create repository"**

You'll see a page with commands like:
```bash
git remote add origin https://github.com/yourusername/fantasy-football.git
git branch -M main
git push -u origin main
```

---

## Step 2: Initialize Git Locally

### 2.1 - Navigate to Project
```bash
cd /path/to/fantasy-football
```

### 2.2 - Initialize Git
```bash
git init
```

This creates a `.git` folder (hidden).

### 2.3 - Verify Git Status
```bash
git status
```

You should see something like:
```
On branch master

No commits yet

Untracked files:
  (use "git add <file>..." to include in what will be committed)
        .gitignore
        README.md
        pom.xml
        src/
        docs/
        ...
```

---

## Step 3: Add Files to Git

### 3.1 - Add All Files
```bash
git add .
```

This stages all files for commit (except those in `.gitignore`).

### 3.2 - Verify What Will Be Committed
```bash
git status
```

You should see all files listed in green as "Changes to be committed".

---

## Step 4: Create Initial Commit

### 4.1 - Commit Staged Files
```bash
git commit -m "feat: Initial commit - Phase 1 complete with player API

- Spring Boot 3 REST API setup
- Player entity with database seeding (~120 players)
- GET /players endpoints
- Integration tests with MockMvc
- H2 in-memory database
- Professional README and documentation

Phase 2 implementation guide included for league and team management.
Ready for GitHub push."
```

### 4.2 - Verify Commit
```bash
git log --oneline
```

You should see your commit listed.

---

## Step 5: Add GitHub Remote

### 5.1 - Add Remote Repository
Replace `yourusername` with your actual GitHub username:

```bash
git remote add origin https://github.com/yourusername/fantasy-football.git
```

### 5.2 - Verify Remote
```bash
git remote -v
```

You should see:
```
origin  https://github.com/yourusername/fantasy-football.git (fetch)
origin  https://github.com/yourusername/fantasy-football.git (push)
```

---

## Step 6: Rename Branch to Main (Optional but Recommended)

### 6.1 - Rename Current Branch
```bash
git branch -M main
```

This renames the default branch from `master` to `main` (GitHub's new default).

---

## Step 7: Push to GitHub

### 7.1 - Push Code
```bash
git push -u origin main
```

**First time?** You may be prompted to authenticate:
- **Option 1 (Recommended):** Use GitHub Personal Access Token
  1. Go to GitHub Settings → Developer settings → Personal access tokens
  2. Click "Generate new token"
  3. Name: `fantasy-football-push`
  4. Scopes: Check `repo` (full control of private repositories)
  5. Click "Generate token"
  6. Copy token and paste when prompted
  7. Save token securely (you can only view it once)

- **Option 2:** Use GitHub CLI
  ```bash
  gh auth login
  # Follow interactive prompts
  ```

### 7.2 - Verify Push
Once complete, go to your GitHub repository URL:
```
https://github.com/yourusername/fantasy-football
```

You should see:
- ✅ All files and folders visible
- ✅ README.md displayed automatically
- ✅ Code available online

---

## Step 8: Verify Repository Structure on GitHub

Check that everything is there:

- ✅ `src/main/java/com/fantasyfootball/` - Source code
- ✅ `src/main/resources/` - Configuration and seed data
- ✅ `src/test/` - Test files
- ✅ `pom.xml` - Maven dependencies
- ✅ `README.md` - Project documentation
- ✅ `.gitignore` - Ignored files
- ✅ `docs/` folder with Phase 2 guides
  - ✅ `PHASE_2_IMPLEMENTATION_GUIDE.md`
  - ✅ `PHASE_2_CHECKLIST.md`
  - ✅ `QUICK_REFERENCE.md`

---

## Step 9: Add Topics & Description (GitHub UI)

Go to your repository on GitHub and:

1. Click **Settings** (top-right)
2. Scroll to **About** section
3. Click **Edit** and add:
   - **Description:** `Zero-cost fantasy football platform for European soccer leagues`
   - **Topics:** `java`, `spring-boot`, `rest-api`, `fantasy-football`, `architecture`, `learning-project`
   - **Website:** (optional - your portfolio)

---

## Post-Push Workflow

### For Future Changes

After making changes locally:

```bash
# Check what changed
git status

# Stage changes
git add .

# Commit with message
git commit -m "feat: Add feature description"

# Push to GitHub
git push
```

### Creating Branches for Phase 2

Once Phase 2 is ready:

```bash
# Create new branch
git checkout -b feature/league-team-management

# Make changes, commit
git add .
git commit -m "feat: Add league and team management APIs"

# Push branch
git push -u origin feature/league-team-management

# Create Pull Request on GitHub UI (optional but good practice)
```

---

## Common Git Commands Reference

```bash
# Check status
git status

# View commit history
git log --oneline

# View specific commit details
git show <commit-hash>

# View differences since last commit
git diff

# Undo last commit (keep changes)
git reset --soft HEAD~1

# Undo last commit (discard changes)
git reset --hard HEAD~1

# Create new branch
git checkout -b branch-name

# Switch branches
git checkout branch-name

# List all branches
git branch -a

# Delete branch locally
git branch -d branch-name

# Delete branch on GitHub
git push origin --delete branch-name

# Pull latest changes
git pull origin main
```

---

## Troubleshooting

### "fatal: not a git repository"
You haven't initialized git. Run:
```bash
git init
```

### "fatal: 'origin' does not appear to be a 'git' repository"
You haven't added the remote. Run:
```bash
git remote add origin https://github.com/yourusername/fantasy-football.git
```

### "Permission denied (publickey)"
You need to set up SSH keys or use HTTPS with a token. Use HTTPS:
```bash
git remote remove origin
git remote add origin https://github.com/yourusername/fantasy-football.git
```

### "error: src refspec main does not match any"
You haven't committed yet. Run:
```bash
git add .
git commit -m "Initial commit"
git branch -M main
git push -u origin main
```

### "Authentication failed"
Make sure you're using:
- HTTPS with Personal Access Token (recommended)
- Or SSH with SSH keys configured
- Password authentication is no longer supported on GitHub

---

## What People Will See on Your GitHub

When someone visits your repository:

1. **Profile Picture & Repository Name**
   - `fantasy-football` by `yourusername`

2. **About Section**
   - Your description + topics

3. **README Preview**
   - Full README.md displayed automatically with formatting

4. **File Structure**
   - Clear folder organization
   - Clean code structure

5. **Documentation**
   - Phase 2 implementation guide visible
   - Quick reference and checklist

6. **Commit History**
   - Shows "Initial commit" with detailed message

---

## GitHub README Tips

Your README.md now displays nicely with:
- ✅ Markdown formatting
- ✅ Badges (License, Java version, Spring Boot version)
- ✅ Quick links to sections
- ✅ Code syntax highlighting
- ✅ Project structure visualization
- ✅ API documentation
- ✅ Development roadmap

---

## Next Steps

1. ✅ Push Phase 1 to GitHub
2. 📖 Share the repository link with others
3. 💻 Implement Phase 2 locally on a new branch
4. 🔀 Create a Pull Request for Phase 2 review
5. 📤 Merge to main once Phase 2 is complete

---

## Additional Resources

- [GitHub Docs](https://docs.github.com/)
- [Git Basics](https://git-scm.com/doc)
- [GitHub Markdown](https://guides.github.com/features/mastering-markdown/)
- [Git Cheat Sheet](https://github.github.com/training-kit/downloads/github-git-cheat-sheet.pdf)

---

## Success Indicators

After pushing, you should see:

✅ Repository appears on your GitHub profile  
✅ All code files visible online  
✅ README.md displays with proper formatting  
✅ Documentation links work  
✅ Green checkmarks next to files (no errors)  
✅ Commit history shows your commit  

---

**Congratulations!** Your Fantasy Football project is now on GitHub! 🎉
