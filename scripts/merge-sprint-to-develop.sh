#!/bin/bash
# merge-sprint-to-develop.sh
# Script pour merger une branche sprint dans develop
# Usage: ./merge-sprint-to-develop.sh 1 "foundation"

set -e

if [ $# -ne 2 ]; then
    echo "❌ Erreur: Nombre d'arguments incorrect"
    echo "Usage: $0 <SPRINT-NUMBER> <SPRINT-DESCRIPTION>"
    echo "Example: $0 1 \"foundation\""
    exit 1
fi

SPRINT_NUM=$1
SPRINT_DESC=$2
BRANCH_NAME="sprint-${SPRINT_NUM}-${SPRINT_DESC// /-}"
TAG_NAME="sprint-${SPRINT_NUM}-completed-$(date +%Y-%m-%d)"

echo "🔄 Merging sprint branch: $BRANCH_NAME"
echo "📋 Sprint ${SPRINT_NUM}: ${SPRINT_DESC}"
echo ""

# Checkout develop and pull latest
echo "📥 Checking out develop and pulling latest..."
git checkout develop
git pull origin develop

# Merge sprint branch
echo "🔀 Merging sprint branch into develop..."
git merge --no-ff $BRANCH_NAME -m "feat(sprint-${SPRINT_NUM}): complete sprint ${SPRINT_NUM} - ${SPRINT_DESC}"

# Create tag
echo "🏷️  Creating tag: $TAG_NAME"
git tag -a $TAG_NAME -m "Sprint ${SPRINT_NUM} completed - ${SPRINT_DESC}"

# Push to remote
echo "📤 Pushing to remote..."
git push origin develop
git push origin $TAG_NAME

echo ""
echo "✅ Sprint ${SPRINT_NUM} merged successfully into develop"
echo "🏷️  Tag created: $TAG_NAME"
echo ""
echo "Next steps:"
echo "  1. Review the merge"
echo "  2. Delete feature branches if needed"
echo "  3. Start next sprint"
echo ""
