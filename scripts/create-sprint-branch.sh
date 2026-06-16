#!/bin/bash
# create-sprint-branch.sh
# Script pour créer une branche sprint
# Usage: ./create-sprint-branch.sh 1 "foundation"

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

echo "🌳 Creating sprint branch: $BRANCH_NAME"
echo "📋 Sprint ${SPRINT_NUM}: ${SPRINT_DESC}"
echo ""

# Checkout develop and pull latest
echo "📥 Checking out develop and pulling latest..."
git checkout develop
git pull origin develop

# Create sprint branch
echo "🔨 Creating sprint branch..."
git checkout -b $BRANCH_NAME

# Push to remote
echo "📤 Pushing to remote..."
git push -u origin $BRANCH_NAME

echo ""
echo "✅ Sprint branch created successfully: $BRANCH_NAME"
echo "🚀 Sprint ${SPRINT_NUM} started"
echo ""
echo "Next steps:"
echo "  1. Create feature branches from this sprint branch"
echo "  2. Assign user stories to team members"
echo "  3. Start development"
echo ""
