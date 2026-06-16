#!/bin/bash
# create-feature-branch.sh
# Script pour créer une branche feature basée sur une User Story
# Usage: ./create-feature-branch.sh US-TEN-001 "creation tenant"

set -e

if [ $# -ne 2 ]; then
    echo "❌ Erreur: Nombre d'arguments incorrect"
    echo "Usage: $0 <USER-STORY-ID> <DESCRIPTION>"
    echo "Example: $0 US-TEN-001 \"creation tenant\""
    exit 1
fi

US_ID=$1
DESCRIPTION=$2
BRANCH_NAME="feature/${US_ID}-${DESCRIPTION// /-}"

echo "🌳 Creating feature branch: $BRANCH_NAME"
echo "📋 User Story: $US_ID"
echo ""

# Checkout develop and pull latest
echo "📥 Checking out develop and pulling latest..."
git checkout develop
git pull origin develop

# Create feature branch
echo "🔨 Creating feature branch..."
git checkout -b $BRANCH_NAME

# Push to remote
echo "📤 Pushing to remote..."
git push -u origin $BRANCH_NAME

echo ""
echo "✅ Feature branch created successfully: $BRANCH_NAME"
echo "🚀 Start working on user story: $US_ID"
echo ""
echo "Next steps:"
echo "  1. Make your changes"
echo "  2. Commit with conventional commit format"
echo "  3. Push your changes"
echo "  4. Create Pull Request"
echo ""
