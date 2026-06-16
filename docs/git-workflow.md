# Git Workflow Strategy - EduManager SaaS

## 📋 Overview

Ce document définit la stratégie de branches Git pour le développement collaboratif d'EduManager SaaS. Le workflow est basé sur les **User Stories** et les **Sprints** définis dans `user-stories.md`.

---

## 🌳 Branch Structure

```
main
 ├── develop
 │   ├── sprint-1-foundation
 │   ├── sprint-2-gestion-base
 │   ├── sprint-3-academique
 │   ├── sprint-4-financier
 │   ├── sprint-5-communication-rh
 │   ├── sprint-6-frontend
 │   └── sprint-7-rapports
 │
 ├── feature/US-TEN-001-creation-tenant
 ├── feature/US-TEN-002-activation-tenant
 ├── feature/US-SEC-001-inscription-super-admin
 ├── feature/US-SEC-002-connexion-multi-tenant
 ├── feature/US-INS-001-inscription-eleve
 └── ...
 │
 ├── hotfix/fix-xxx
 └── release/v1.0.0
```

---

## 🔄 Branch Types

### 1. Main Branches

#### `main` (Production)
- **Statut**: Protégée
- **Rôle**: Code déployé en production
- **Règles**: 
  - Seuls les merges depuis `develop` ou `release/*` sont autorisés
  - Pull Request obligatoire avec approbation
  - Tests CI/CD doivent passer
  - Pas de commits directs

#### `develop` (Intégration)
- **Statut**: Protégée
- **Rôle**: Intégration continue de toutes les features
- **Règles**:
  - Seuls les merges depuis `feature/*` ou `sprint-*` sont autorisés
  - Pull Request obligatoire avec approbation
  - Tests CI/CD doivent passer
  - Pas de commits directs

---

### 2. Sprint Branches

Les branches de sprint sont créées au début de chaque sprint et fusionnées dans `develop` à la fin.

#### Naming Convention
```
sprint-{number}-{short-description}
```

**Exemples:**
- `sprint-1-foundation`
- `sprint-2-gestion-base`
- `sprint-3-academique`

#### Lifecycle
1. Créée à partir de `develop` au début du sprint
2. Les features du sprint sont mergées dans cette branche
3. À la fin du sprint, mergée dans `develop`
4. Supprimée après merge

---

### 3. Feature Branches

Chaque **User Story** a sa propre branche feature.

#### Naming Convention
```
feature/US-{MODULE}-{NUMBER}-{short-description}
```

**Exemples:**
- `feature/US-TEN-001-creation-tenant`
- `feature/US-SEC-002-connexion-multi-tenant`
- `feature/US-INS-001-inscription-eleve`
- `feature/US-ACA-003-saisie-notes`

#### Lifecycle
1. Créée à partir de la branche sprint correspondante
2. Développement de la feature
3. Pull Request vers la branche sprint
4. Après approbation et tests passants, merge
5. Supprimée après merge

---

### 4. Hotfix Branches

Pour les corrections urgentes en production.

#### Naming Convention
```
hotfix/{issue-id}-{short-description}
```

**Exemples:**
- `hotfix/BUG-001-fix-login-error`
- `hotfix/SEC-002-patch-security-vulnerability`

#### Lifecycle
1. Créée à partir de `main`
2. Correction du bug
3. Merge dans `main` ET `develop`
4. Supprimée après merge

---

### 5. Release Branches

Pour préparer une nouvelle version.

#### Naming Convention
```
release/v{major}.{minor}.{patch}
```

**Exemples:**
- `release/v1.0.0`
- `release/v1.1.0`

#### Lifecycle
1. Créée à partir de `develop`
2. Finalisation de la version (tests, documentation)
3. Merge dans `main` (tag)
4. Merge dans `develop`
5. Supprimée après merge

---

## 📅 Sprint Branch Mapping

### Sprint 1 - Foundation (4 semaines)
**Branche Sprint:** `sprint-1-foundation`

**Features:**
- `feature/US-TEN-001-creation-tenant`
- `feature/US-TEN-002-activation-tenant`
- `feature/US-SEC-001-inscription-super-admin`
- `feature/US-SEC-002-connexion-multi-tenant`
- `feature/US-SEC-003-rafraichissement-token`
- `feature/US-SEC-004-deconnexion`

**Commandes:**
```bash
# Créer la branche sprint
git checkout develop
git pull origin develop
git checkout -b sprint-1-foundation

# Créer une feature
git checkout sprint-1-foundation
git checkout -b feature/US-TEN-001-creation-tenant

# Après développement, PR vers sprint-1-foundation
```

---

### Sprint 2 - Gestion de Base (4 semaines)
**Branche Sprint:** `sprint-2-gestion-base`

**Features:**
- `feature/US-TEN-005-configuration-tenant`
- `feature/US-TEN-006-invitation-utilisateurs`
- `feature/US-SEC-005-gestion-roles-permissions`
- `feature/US-INS-001-inscription-eleve`
- `feature/US-INS-007-creation-classe`

**Commandes:**
```bash
# Créer la branche sprint
git checkout develop
git pull origin develop
git checkout -b sprint-2-gestion-base
```

---

### Sprint 3 - Académique (4 semaines)
**Branche Sprint:** `sprint-3-academique`

**Features:**
- `feature/US-ACA-001-creation-matiere`
- `feature/US-ACA-002-assignation-enseignants`
- `feature/US-ACA-003-saisie-notes`
- `feature/US-ACA-005-consultation-notes`
- `feature/US-ACA-006-generation-bulletin`

---

### Sprint 4 - Financier (4 semaines)
**Branche Sprint:** `sprint-4-financier`

**Features:**
- `feature/US-FIN-001-configuration-frais`
- `feature/US-FIN-002-generation-factures`
- `feature/US-FIN-003-enregistrement-paiements`
- `feature/US-FIN-004-suivi-impayes`

---

### Sprint 5 - Communication & RH (4 semaines)
**Branche Sprint:** `sprint-5-communication-rh`

**Features:**
- `feature/US-HR-001-creation-enseignant`
- `feature/US-COM-001-envoi-messages`
- `feature/US-COM-003-publication-annonces`
- `feature/US-ACA-009-creation-emploi-du-temps`

---

### Sprint 6 - Frontend (4 semaines)
**Branche Sprint:** `sprint-6-frontend`

**Features:**
- `feature/US-FE-001-dashboard`
- `feature/US-FE-002-gestion-eleves`
- `feature/US-FE-003-gestion-notes`
- `feature/US-FE-004-espace-parent`

---

### Sprint 7 - Rapports & Optimisation (4 semaines)
**Branche Sprint:** `sprint-7-rapports`

**Features:**
- `feature/US-RPT-001-rapport-effectifs`
- `feature/US-RPT-002-rapport-resultats`
- `feature/US-RPT-003-rapport-financier`

---

## 🔧 Git Commands Reference

### Initial Setup
```bash
# Cloner le repository
git clone https://github.com/your-org/edumanager.git
cd edumanager

# Configurer les branches upstream
git branch --set-upstream-to=origin/main main
git branch --set-upstream-to=origin/develop develop
```

### Créer une Feature Branch
```bash
# Mettre à jour develop
git checkout develop
git pull origin develop

# Créer la branche sprint (si elle n'existe pas)
git checkout -b sprint-X-nom-du-sprint

# Créer la feature
git checkout sprint-X-nom-du-sprint
git checkout -b feature/US-MODULE-XXX-description

# Pousser la branche
git push -u origin feature/US-MODULE-XXX-description
```

### Workflow de Développement
```bash
# 1. Travailler sur la feature
git checkout feature/US-MODULE-XXX-description
# ... faire les modifications ...

# 2. Commit avec message conventionnel
git add .
git commit -m "feat(US-TEN-001): implement tenant creation logic"

# 3. Push
git push

# 4. Créer Pull Request vers la branche sprint
# Via GitHub/GitLab interface
```

### Convention de Messages de Commit

Suivre le format **Conventional Commits**:

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types:**
- `feat`: Nouvelle feature
- `fix`: Correction de bug
- `docs`: Documentation
- `style`: Formatage, point-virgule manquants, etc.
- `refactor`: Refactoring
- `test`: Ajout de tests
- `chore`: Mise à jour des tâches de build, etc.

**Exemples:**
```
feat(US-TEN-001): implement tenant creation logic

- Add Tenant aggregate root with business rules
- Implement CreateTenantUseCase
- Add tenant validation
- Add domain events for tenant creation

Closes #US-TEN-001
```

```
fix(US-SEC-002): fix JWT token validation

- Fix token expiration check
- Add proper error handling

Fixes #BUG-001
```

---

### Merge d'une Feature dans Sprint
```bash
# Via Pull Request (recommandé)
# 1. Créer PR sur GitHub/GitLab
# 2. Demander review
# 3. Après approbation, merge avec "Squash and merge"

# Ou en ligne de commande (si autorisé)
git checkout sprint-X-nom-du-sprint
git pull origin sprint-X-nom-du-sprint
git merge --squash feature/US-MODULE-XXX-description
git commit -m "feat(US-MODULE-XXX): complete feature implementation"
git push
```

### Merge d'un Sprint dans Develop
```bash
# À la fin du sprint
git checkout develop
git pull origin develop
git merge --no-ff sprint-X-nom-du-sprint
git tag sprint-X-completed-YYYY-MM-DD
git push origin develop
git push origin sprint-X-completed-YYYY-MM-DD
```

### Créer une Release
```bash
# À partir de develop
git checkout develop
git pull origin develop
git checkout -b release/v1.0.0

# Préparer la release (versioning, changelog)
# ...

# Merge dans main
git checkout main
git merge --no-ff release/v1.0.0
git tag -a v1.0.0 -m "Release v1.0.0"
git push origin main
git push origin v1.0.0

# Merge dans develop
git checkout develop
git merge --no-ff release/v1.0.0
git push origin develop
```

### Hotfix Workflow
```bash
# À partir de main
git checkout main
git pull origin main
git checkout -b hotfix/BUG-001-fix-login-error

# Correction
# ...

# Merge dans main
git checkout main
git merge --no-ff hotfix/BUG-001-fix-login-error
git tag -a v1.0.1 -m "Hotfix v1.0.1"
git push origin main
git push origin v1.0.1

# Merge dans develop
git checkout develop
git merge --no-ff hotfix/BUG-001-fix-login-error
git push origin develop
```

---

## 📋 Pull Request Template

```markdown
## Description
[Brief description of the changes]

## User Story
- **ID**: US-MODULE-XXX
- **Title**: [User story title]
- **Link**: [Link to user story in issue tracker]

## Type of Change
- [ ] New feature
- [ ] Bug fix
- [ ] Refactoring
- [ ] Documentation
- [ ] Hotfix

## Changes Made
- [ ] Code changes
- [ ] Database migrations
- [ ] Configuration changes
- [ ] Documentation updates

## Testing
- [ ] Unit tests added/updated
- [ ] Integration tests added/updated
- [ ] Manual testing performed
- [ ] All tests passing

## Checklist
- [ ] Code follows project style guidelines
- [ ] Self-review performed
- [ ] Comments added for complex logic
- [ ] Documentation updated
- [ ] No new warnings generated
- [ ] User story acceptance criteria met

## Screenshots (if applicable)
[Add screenshots for UI changes]

## Additional Notes
[Any additional information]
```

---

## 🎯 Best Practices

### Pour les Développeurs
1. **Toujours travailler sur une feature branch**, jamais directement sur develop ou main
2. **Faire des commits fréquents** avec des messages clairs
3. **Pusher régulièrement** pour éviter les conflits
4. **Créer des PRs des que possible** pour obtenir du feedback tôt
5. **Rebase develop** avant de merge pour éviter les commits inutiles
6. **Supprimer les branches** après merge pour garder le repo propre

### Pour les Reviewers
1. **Revoir rapidement** les PRs pour ne pas bloquer le développement
2. **Vérifier les critères d'acceptation** de la user story
3. **Vérifier la qualité du code** (clean code, tests, documentation)
4. **Demander des modifications** si nécessaire, mais être constructif
5. **Approuver seulement** quand tous les critères sont remplis

### Pour le Scrum Master
1. **Créer les branches sprint** au début de chaque sprint
2. **Suivre la progression** des features dans chaque sprint
3. **Coordonner les merges** à la fin du sprint
4. **Gérer les conflits** entre branches
5. **Assurer la qualité** avant le merge dans develop

---

## 🔍 Branch Protection Rules (GitHub/GitLab)

### Branch `main`
- ✅ Require pull request before merging
- ✅ Require approval from at least 2 reviewers
- ✅ Require status checks to pass (CI/CD)
- ✅ Require branches to be up to date before merging
- ❌ Restrict who can push (maintainers only)

### Branch `develop`
- ✅ Require pull request before merging
- ✅ Require approval from at least 1 reviewer
- ✅ Require status checks to pass (CI/CD)
- ✅ Require branches to be up to date before merging
- ❌ Restrict who can push (maintainers only)

### Branch `sprint-*`
- ✅ Require pull request before merging
- ✅ Require approval from at least 1 reviewer
- ✅ Require status checks to pass (CI/CD)
- ✅ Allow force push (for cleanup)

---

## 📊 Branch Status Dashboard

| Sprint | Branch | Status | Features | Start Date | End Date |
|--------|--------|--------|----------|------------|----------|
| 1 | sprint-1-foundation | 🟢 Active | 6 | 2024-01-01 | 2024-01-28 |
| 2 | sprint-2-gestion-base | ⚪ Not Started | 5 | 2024-01-29 | 2024-02-25 |
| 3 | sprint-3-academique | ⚪ Not Started | 5 | 2024-02-26 | 2024-03-24 |
| 4 | sprint-4-financier | ⚪ Not Started | 4 | 2024-03-25 | 2024-04-21 |
| 5 | sprint-5-communication-rh | ⚪ Not Started | 4 | 2024-04-22 | 2024-05-19 |
| 6 | sprint-6-frontend | ⚪ Not Started | 4 | 2024-05-20 | 2024-06-16 |
| 7 | sprint-7-rapports | ⚪ Not Started | 3 | 2024-06-17 | 2024-07-14 |

---

## 🚀 Quick Start Script

```bash
#!/bin/bash
# create-feature-branch.sh
# Usage: ./create-feature-branch.sh US-TEN-001 "creation tenant"

if [ $# -ne 2 ]; then
    echo "Usage: $0 <USER-STORY-ID> <DESCRIPTION>"
    echo "Example: $0 US-TEN-001 \"creation tenant\""
    exit 1
fi

US_ID=$1
DESCRIPTION=$2
BRANCH_NAME="feature/${US_ID}-${DESCRIPTION// /-}"

echo "Creating feature branch: $BRANCH_NAME"

# Checkout develop and pull latest
git checkout develop
git pull origin develop

# Create feature branch
git checkout -b $BRANCH_NAME

# Push to remote
git push -u origin $BRANCH_NAME

echo "Feature branch created successfully: $BRANCH_NAME"
echo "Start working on user story: $US_ID"
```

---

## 📝 Notes

- Ce workflow peut être adapté selon les besoins de l'équipe
- Les branches doivent être supprimées après merge pour maintenir un repo propre
- Utiliser des tags pour marquer les milestones importantes
- Le CI/CD doit être configuré pour tester toutes les branches
- Les conflits doivent être résolus rapidement pour ne pas bloquer l'équipe
