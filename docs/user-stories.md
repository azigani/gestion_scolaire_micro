# EduManager SaaS - User Stories

## 📋 Introduction

Ce document contient toutes les user stories pour le développement collaboratif d'EduManager SaaS. Chaque story suit le format standard :

- **En tant que** [rôle]
- **Je veux** [action]
- **Afin de** [objectif]

Avec des critères d'acceptation (Acceptance Criteria) clairs.

---

## 🏢 MODULE TENANT (Gestion Multi-tenant)

### Epic: Gestion des Tenants (Écoles)

#### US-TEN-001: Création d'un nouveau tenant
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Super Admin  
**Je veux** créer un nouveau tenant (école)  
**Afin de permettre à une nouvelle école d'utiliser la plateforme

**Critères d'acceptation:**
- [ ] Je peux créer un tenant avec un slug unique (ex: ecole-jean-jaures)
- [ ] Je peux définir le nom de l'école
- [ ] Je peux configurer les informations de contact (email, téléphone, adresse)
- [ ] Je peux définir un domaine personnalisé (ex: ecole-jaures.com)
- [ ] Je peux définir un sous-domaine (ex: jaures.edumanager.com)
- [ ] Je peux uploader un logo
- [ ] Je peux personnaliser les couleurs de l'interface
- [ ] Le système crée automatiquement un schéma de base de données isolé
- [ ] Le système génère automatiquement les configurations par défaut
- [ ] Je reçois une confirmation avec les identifiants du tenant créé
- [ ] Le slug doit être unique et valide (lettres minuscules, chiffres, tirets)
- [ ] Le domaine doit être unique et valide
- [ ] Le sous-domaine doit être unique et valide

**Dépendances:** Aucune

---

#### US-TEN-002: Activation d'un tenant
**Priorité:** 🔴 Critique  
**Story Points:** 5

**En tant que** Super Admin  
**Je veux** activer un tenant en attente  
**Afin de permettre à l'école d'utiliser la plateforme

**Critères d'acceptation:**
- [ ] Je peux voir la liste des tenants en attente (PENDING)
- [ ] Je peux activer un tenant en un clic
- [ ] Le système change le statut de PENDING à ACTIVE
- [ ] Le système publie un événement TenantActivated
- [ ] L'administrateur du tenant reçoit un email de confirmation
- [ ] Je ne peux pas activer un tenant déjà actif
- [ ] Je ne peux pas activer un tenant suspendu ou terminé

**Dépendances:** US-TEN-001

---

#### US-TEN-003: Suspension d'un tenant
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Super Admin  
**Je veux** suspendre un tenant actif  
**Afin de bloquer temporairement l'accès en cas de problème (non-paiement, abus, etc.)

**Critères d'acceptation:**
- [ ] Je peux voir la liste des tenants actifs
- [ ] Je peux suspendre un tenant en fournissant une raison
- [ ] Le système change le statut de ACTIVE à SUSPENDED
- [ ] Le système publie un événement TenantSuspended
- [ ] L'administrateur du tenant reçoit un email de notification
- [ ] Les utilisateurs du tenant ne peuvent plus se connecter
- [ ] Les données du tenant restent accessibles en lecture seule pour l'admin
- [ ] Je peux réactiver un tenant suspendu

**Dépendances:** US-TEN-002

---

#### US-TEN-004: Gestion des abonnements
**Priorité:** 🟡 Haute  
**Story Points:** 8

**En tant que** Super Admin  
**Je veux** gérer les abonnements des tenants  
**Afin de contrôler l'accès aux fonctionnalités selon le plan

**Critères d'acceptation:**
- [ ] Je peux voir le plan d'abonnement actuel de chaque tenant
- [ ] Je peux changer le plan (STARTER → PROFESSIONAL → ENTERPRISE)
- [ ] Je peux modifier les limites (max étudiants, max enseignants, max staff)
- [ ] Je peux prolonger la date de fin d'abonnement
- [ ] Le système vérifie automatiquement que le tenant respecte les nouvelles limites
- [ ] Je peux voir l'historique des changements d'abonnement
- [ ] Le système envoie une notification avant l'expiration de l'abonnement

**Dépendances:** US-TEN-001

---

#### US-TEN-005: Configuration du tenant
**Priorité:** 🟢 Moyenne  
**Story Points:** 5

**En tant qu'Administrateur du tenant  
**Je veux** configurer les paramètres de mon école  
**Afin d'adapter la plateforme à nos besoins

**Critères d'acceptation:**
- [ ] Je peux modifier le fuseau horaire
- [ ] Je peux modifier la langue par défaut
- [ ] Je peux modifier la devise
- [ ] Je peux modifier les couleurs de l'interface
- [ ] Je peux modifier le logo
- [ ] Je peux activer/désactiver les inscriptions en ligne
- [ ] Je peux configurer les paramètres de mot de passe
- [ ] Je peux configurer les notifications par email/SMS
- [ ] Les modifications sont appliquées immédiatement

**Dépendances:** US-TEN-002

---

#### US-TEN-006: Invitation d'utilisateurs
**Priorité:** 🟡 Haute  
**Story Points:** 8

**En tant qu'Administrateur du tenant  
**Je veux** inviter des utilisateurs à rejoindre mon école  
**Afin de leur donner accès à la plateforme

**Critères d'acceptation:**
- [ ] Je peux inviter un utilisateur par email
- [ ] Je peux définir le rôle de l'utilisateur (ADMIN, ENSEIGNANT, SECRETAIRE, etc.)
- [ ] Je peux définir les permissions spécifiques
- [ ] Le système génère un lien d'invitation sécurisé
- [ ] Le lien expire après 7 jours
- [ ] L'invité reçoit un email avec le lien
- [ ] L'invité peut créer son compte via le lien
- [ ] Je peux voir la liste des invitations en attente
- [ ] Je peux annuler une invitation
- [ ] Je peux réinviter un utilisateur

**Dépendances:** US-TEN-002, US-SEC-001

---

## 🔐 MODULE SÉCURITÉ

### Epic: Authentification Multi-tenant

#### US-SEC-001: Inscription d'un super admin
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Super Admin  
**Je veux** créer mon compte lors de la création du tenant  
**Afin d'être le premier administrateur de l'école

**Critères d'acceptation:**
- [ ] Je peux créer mon compte avec email et mot de passe
- [ ] Le mot de passe doit respecter les règles de complexité
- [ ] Le système hash le mot de passe avec BCrypt
- [ ] Je reçois un email de vérification
- [ ] Je dois vérifier mon email avant de pouvoir me connecter
- [ ] Mon compte est automatiquement lié au tenant
- [ ] J'ai le rôle TENANT_ADMIN par défaut
- [ ] Je dois changer mon mot de passe à la première connexion

**Dépendances:** US-TEN-001

---

#### US-SEC-002: Connexion multi-tenant
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant qu'Utilisateur  
**Je veux** me connecter avec mon email et mot de passe  
**Afin d'accéder à mon espace

**Critères d'acceptation:**
- [ ] Je peux me connecter avec email et mot de passe
- [ ] Je dois spécifier mon tenant (via slug, sous-domaine ou domaine)
- [ ] Le système vérifie que le tenant existe et est actif
- [ ] Le système vérifie mes identifiants dans le contexte du tenant
- [ ] En cas de succès, je reçois un JWT token
- [ ] Le token contient mon userId, tenantId, rôles et permissions
- [ ] Le token expire après 1 heure
- [ ] Je reçois un refresh token valide 7 jours
- [ ] En cas d'échec, je reçois un message d'erreur clair
- [ ] Après 3 échecs, mon compte est verrouillé 30 minutes

**Dépendances:** US-SEC-001

---

#### US-SEC-003: Rafraîchissement du token
**Priorité:** 🔴 Critique  
**Story Points:** 5

**En tant qu'Utilisateur  
**Je veux** rafraîchir mon token sans me reconnecter  
**Afin de rester connecté plus longtemps

**Critères d'acceptation:**
- [ ] Je peux rafraîchir mon token avec le refresh token
- [ ] Le système vérifie que le refresh token est valide
- [ ] Le système vérifie que le refresh token n'est pas révoqué
- [ ] Je reçois un nouveau JWT token
- [ ] Le refresh token est révoqué après utilisation
- [ ] Je peux avoir plusieurs refresh tokens actifs (multi-device)
- [ ] Je peux révoquer tous mes refresh tokens (logout everywhere)

**Dépendances:** US-SEC-002

---

#### US-SEC-004: Déconnexion
**Priorité:** 🔴 Critique  
**Story Points:** 3

**En tant qu'Utilisateur  
**Je veux** me déconnecter  
**Afin de sécuriser mon compte

**Critères d'acceptation:**
- [ ] Je peux me déconnecter en un clic
- [ ] Le système révoque mon refresh token
- [ ] Le JWT token reste valide jusqu'à expiration (stateless)
- [ ] Je peux me déconnecter de tous les appareils
- [ ] Le système journalise la déconnexion

**Dépendances:** US-SEC-002

---

#### US-SEC-005: Gestion des rôles et permissions
**Priorité:** 🟡 Haute  
**Story Points:** 8

**En tant qu'Administrateur du tenant  
**Je veux** gérer les rôles et permissions des utilisateurs  
**Afin de contrôler l'accès aux fonctionnalités

**Critères d'acceptation:**
- [ ] Je peux voir la liste des rôles disponibles
- [ ] Je peux créer des rôles personnalisés
- [ ] Je peux assigner des permissions à un rôle
- [ ] Je peux voir la liste des permissions disponibles
- [ ] Je peux assigner un rôle à un utilisateur
- [ ] Je peux modifier le rôle d'un utilisateur
- [ ] Je peux voir les permissions d'un utilisateur
- [ ] Les permissions sont vérifiées automatiquement à chaque requête
- [ ] Je ne peux pas modifier mes propres permissions

**Dépendances:** US-TEN-006

---

#### US-SEC-006: Réinitialisation du mot de passe
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant qu'Utilisateur  
**Je veux** réinitialiser mon mot de passe oublié  
**Afin de pouvoir me reconnecter

**Critères d'acceptation:**
- [ ] Je peux demander une réinitialisation via email
- [ ] Je dois spécifier mon tenant
- [ ] Le système génère un token de réinitialisation
- [ ] Le token expire après 1 heure
- [ ] Je reçois un email avec le lien de réinitialisation
- [ ] Le lien me permet de définir un nouveau mot de passe
- [ ] Le nouveau mot de passe doit respecter les règles de complexité
- [ ] Le token est consommé après utilisation
- [ ] Je reçois une confirmation après réinitialisation

**Dépendances:** US-SEC-002

---

#### US-SEC-007: Changement de mot de passe
**Priorité:** 🟡 Haute  
**Story Points:** 3

**En tant qu'Utilisateur  
**Je veux** changer mon mot de passe  
**Afin de sécuriser mon compte

**Critères d'acceptation:**
- [ ] Je peux changer mon mot de passe depuis mon profil
- [ ] Je dois fournir mon mot de passe actuel
- [ ] Le nouveau mot de passe doit respecter les règles de complexité
- [ ] Le nouveau mot de passe ne peut pas être identique aux 3 derniers
- [ ] Je reçois une confirmation après changement
- [ ] Le système journalise le changement

**Dépendances:** US-SEC-002

---

#### US-SEC-008: Expiration du mot de passe
**Priorité:** 🟢 Moyenne  
**Story Points:** 5

**En tant qu'Administrateur du tenant  
**Je veux** forcer l'expiration des mots de passe  
**Afin d'améliorer la sécurité

**Critères d'acceptation:**
- [ ] Je peux configurer la période d'expiration (ex: 90 jours)
- [ ] Les utilisateurs sont notifiés 7 jours avant l'expiration
- [ ] À l'expiration, l'utilisateur doit changer son mot de passe
- [ ] L'utilisateur peut continuer à se connecter après expiration (avec avertissement)
- [ ] Je peux forcer le changement immédiat pour tous les utilisateurs
- [ ] Je peux exempter certains utilisateurs

**Dépendances:** US-SEC-007

---

## 📚 MODULE INSCRIPTION (ENROLLMENT)

### Epic: Gestion des Élèves

#### US-INS-001: Inscription d'un nouvel élève
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Secrétaire  
**Je veux** inscrire un nouvel élève  
**Afin de l'ajouter à l'école

**Critères d'acceptation:**
- [ ] Je peux saisir les informations de l'élève (nom, prénom, date de naissance, sexe)
- [ ] Je peux saisir le numéro national d'identité
- [ ] Je peux saisir l'adresse de l'élève
- [ ] Je peux uploader une photo
- [ ] Je peux ajouter les informations des tuteurs (parents)
- [ ] Je peux ajouter jusqu'à 4 tuteurs
- [ ] Je peux définir un tuteur principal
- [ ] Je peux assigner l'élève à une classe
- [ ] Le système vérifie que la classe n'a pas atteint sa capacité maximale
- [ ] Le système génère un numéro d'étudiant unique
- [ ] Le système crée automatiquement le dossier médical vide
- [ ] Je reçois une confirmation avec les détails de l'inscription

**Dépendances:** US-TEN-002, US-SEC-002

---

#### US-INS-002: Réinscription d'un élève
**Priorité:** 🔴 Critique  
**Story Points:** 5

**En tant que** Secrétaire  
**Je veux** réinscrire un élève pour la nouvelle année scolaire  
**Afin de le garder dans l'école

**Critères d'acceptation:**
- [ ] Je peux voir la liste des élèves de l'année précédente
- [ ] Je peux sélectionner un élève à réinscrire
- [ ] Le système vérifie que tous les frais de l'année précédente sont payés
- [ ] Je peux assigner l'élève à une nouvelle classe
- [ ] Le système conserve l'historique des inscriptions
- [ ] Le système met à jour le statut de l'élève
- [ ] Je reçois une confirmation de réinscription

**Dépendances:** US-INS-001, US-FIN-001

---

#### US-INS-003: Transfert d'un élève
**Priorité:** 🟡 Haute  
**Story Points:** 8

**En tant que** Secrétaire  
**Je veux** transférer un élève vers une autre classe  
**Afin de le changer de classe en cours d'année

**Critères d'acceptation:**
- [ ] Je peux voir la liste des élèves inscrits
- [ ] Je peux sélectionner un élève à transférer
- [ ] Je peux choisir la nouvelle classe
- [ ] Le système vérifie que la nouvelle classe n'a pas atteint sa capacité
- [ ] Le système vérifie que l'élève n'est pas déjà dans cette classe
- [ ] Le système conserve l'historique des transferts
- [ ] Le système publie un événement StudentTransferred
- [ ] Je dois fournir une raison du transfert
- [ ] Je reçois une confirmation du transfert

**Dépendances:** US-INS-001

---

#### US-INS-004: Suspension d'un élève
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Directeur  
**Je veux** suspendre un élève  
**Afin de le sanctionner temporairement

**Critères d'acceptation:**
- [ ] Je peux voir la liste des élèves actifs
- [ ] Je peux suspendre un élève en fournissant une raison
- [ ] Le système change le statut de ACTIVE à SUSPENDED
- [ ] Le système publie un événement StudentSuspended
- [ ] L'élève ne peut plus se connecter
- [ ] Les parents reçoivent une notification
- [ ] Je peux réactiver un élève suspendu
- [ ] L'historique des suspensions est conservé

**Dépendances:** US-INS-001

---

#### US-INS-005: Consultation du dossier élève
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Enseignant / Parent / Élève  
**Je veux** consulter le dossier d'un élève  
**Afin de voir ses informations

**Critères d'acceptation:**
- [ ] Je peux voir les informations personnelles de l'élève
- [ ] Je peux voir les tuteurs de l'élève
- [ ] Je peux voir l'historique des inscriptions
- [ ] Je peux voir la classe actuelle
- [ ] Je peux voir le dossier médical (si j'ai la permission)
- [ ] Je peux voir les absences
- [ ] Je peux voir les notes
- [ ] Je peux voir les bulletins
- [ ] L'accès est restreint selon mes permissions

**Dépendances:** US-INS-001

---

#### US-INS-006: Gestion des tuteurs
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Secrétaire  
**Je veux** gérer les tuteurs d'un élève  
**Afin de mettre à jour les informations des parents

**Critères d'acceptation:**
- [ ] Je peux ajouter un tuteur à un élève
- [ ] Je peux modifier les informations d'un tuteur
- [ ] Je peux supprimer un tuteur
- [ ] Je peux définir le tuteur principal
- [ ] Je ne peux pas avoir plus de 4 tuteurs par élève
- [ ] Je ne peux pas supprimer le tuteur principal s'il est le seul
- [ ] Les modifications sont journalisées

**Dépendances:** US-INS-001

---

### Epic: Gestion des Classes

#### US-INS-007: Création d'une classe
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Directeur  
**Je veux** créer une nouvelle classe  
**Afin d'organiser les élèves

**Critères d'acceptation:**
- [ ] Je peux définir le nom de la classe (ex: 6ème A)
- [ ] Je peux assigner la classe à un niveau (ex: 6ème)
- [ ] Je peux définir la capacité maximale de la classe
- [ ] Je peux assigner un professeur principal
- [ ] Je peux assigner une salle de classe
- [ ] Le système vérifie que le nom est unique pour l'année scolaire
- [ ] Le système vérifie que le professeur principal n'est pas déjà principal d'une autre classe
- [ ] Je reçois une confirmation de création

**Dépendances:** US-TEN-002, US-HR-001

---

#### US-INS-008: Modification d'une classe
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Directeur  
**Je veux** modifier une classe existante  
**Afin d'adapter l'organisation

**Critères d'acceptation:**
- [ ] Je peux modifier le nom de la classe
- [ ] Je peux modifier la capacité maximale
- [ ] Je peux modifier le professeur principal
- [ ] Je peux modifier la salle de classe
- [ ] Le système vérifie que la nouvelle capacité n'est pas inférieure au nombre d'élèves actuels
- [ ] Les modifications sont journalisées

**Dépendances:** US-INS-007

---

#### US-INS-009: Consultation des classes
**Priorité:** 🟡 Haute  
**Story Points:** 3

**En tant que** Enseignant / Secrétaire  
**Je veux** voir la liste des classes  
**Afin de connaître l'organisation

**Critères d'acceptation:**
- [ ] Je peux voir la liste des classes de l'année scolaire actuelle
- [ ] Je peux filtrer par niveau
- [ ] Je peux voir le nombre d'élèves dans chaque classe
- [ ] Je peux voir le professeur principal
- [ ] Je peux voir la salle de classe
- [ ] Je peux voir les détails d'une classe

**Dépendances:** US-INS-007

---

## 📖 MODULE ACADÉMIQUE (ACADEMIC)

### Epic: Gestion des Matières

#### US-ACA-001: Création d'une matière
**Priorité:** 🔴 Critique  
**Story Points:** 5

**En tant que** Directeur  
**Je veux** créer une nouvelle matière  
**Afin de l'enseigner dans l'école

**Critères d'acceptation:**
- [ ] Je peux définir le code de la matière (ex: MAT)
- [ ] Je peux définir le nom de la matière (ex: Mathématiques)
- [ ] Je peux assigner la matière à un niveau
- [ ] Je peux définir le nombre d'heures hebdomadaires
- [ ] Je peux définir le coefficient
- [ ] Le code doit être unique
- [ ] Je reçois une confirmation de création

**Dépendances:** US-TEN-002

---

#### US-ACA-002: Assignation d'enseignants aux matières
**Priorité:** 🔴 Critique  
**Story Points:** 5

**En tant que** Directeur  
**Je veux** assigner des enseignants aux matières  
**Afin de définir qui enseigne quoi

**Critères d'acceptation:**
- [ ] Je peux voir la liste des matières
- [ ] Je peux assigner un enseignant à une matière
- [ ] Je peux assigner un enseignant à plusieurs classes pour une matière
- [ ] Le système vérifie que l'enseignant n'a pas de conflit d'horaire
- [ ] Je peux modifier l'assignation
- [ ] Je peux supprimer l'assignation
- [ ] Les modifications sont journalisées

**Dépendances:** US-ACA-001, US-HR-001

---

### Epic: Gestion des Notes

#### US-ACA-003: Saisie des notes
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Enseignant  
**Je veux** saisir les notes de mes élèves  
**Afin d'évaluer leur progression

**Critères d'acceptation:**
- [ ] Je peux voir la liste de mes classes
- [ ] Je peux sélectionner une classe et une matière
- [ ] Je peux voir la liste des élèves
- [ ] Je peux saisir une note pour chaque élève
- [ ] Je peux définir le barème de l'évaluation (ex: sur 20)
- [ ] Le système vérifie que la note est valide (entre 0 et le barème)
- [ ] Je peux ajouter un commentaire pour chaque note
- [ ] Je peux enregistrer les notes
- [ ] Le système calcule automatiquement la moyenne
- [ ] Les modifications sont journalisées

**Dépendances:** US-ACA-002, US-INS-001

---

#### US-ACA-004: Modification des notes
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Enseignant  
**Je veux** modifier une note saisie  
**Afin de corriger une erreur

**Critères d'acceptation:**
- [ ] Je peux modifier une note avant la clôture du trimestre
- [ ] Je dois fournir une raison de la modification
- [ ] Le système conserve l'ancienne note
- [ ] Le système journalise la modification
- [ ] Je ne peux pas modifier une note après clôture du trimestre (sauf autorisation spéciale)
- [ ] Les parents sont notifiés de la modification

**Dépendances:** US-ACA-003

---

#### US-ACA-005: Consultation des notes
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Parent / Élève / Enseignant  
**Je veux** consulter les notes  
**Afin de suivre la progression

**Critères d'acceptation:**
- [ ] Je peux voir les notes de l'élève
- [ ] Je peux voir les notes par matière
- [ ] Je peux voir les notes par trimestre
- [ ] Je peux voir la moyenne générale
- [ ] Je peux voir la moyenne par matière
- [ ] Je peux voir le classement de l'élève
- [ ] Je peux voir les commentaires des enseignants
- [ ] L'accès est restreint selon mes permissions

**Dépendances:** US-ACA-003

---

#### US-ACA-006: Génération du bulletin
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Secrétaire / Parent  
**Je veux** générer un bulletin de notes  
**Afin d'avoir un récapitulatif officiel

**Critères d'acceptation:**
- [ ] Je peux générer un bulletin pour un élève
- [ ] Je peux sélectionner le trimestre
- [ ] Le bulletin contient toutes les matières
- [ ] Le bulletin contient les notes
- [ ] Le bulletin contient les moyennes
- [ ] Le bulletin contient les appréciations
- [ ] Le bulletin contient le classement
- [ ] Le bulletin contient les absences
- [ ] Le bulletin est généré en PDF
- [ ] Le bulletin peut être signé numériquement
- [ ] Le bulletin peut être envoyé par email

**Dépendances:** US-ACA-003, US-ACA-005, US-ACA-007

---

### Epic: Gestion des Absences

#### US-ACA-007: Saisie des absences
**Priorité:** 🔴 Critique  
**Story Points:** 5

**En tant que** Enseignant / Surveillant  
**Je veux** saisir les absences des élèves  
**Afin de suivre leur assiduité

**Critères d'acceptation:**
- [ ] Je peux voir la liste de mes classes
- [ ] Je peux sélectionner une classe et une date
- [ ] Je peux marquer les élèves absents
- [ ] Je peux définir le type d'absence (justifiée, non justifiée, retard)
- [ ] Je peux ajouter un commentaire
- [ ] Le système calcule automatiquement le taux d'absence
- [ ] Les parents sont notifiés automatiquement
- [ ] Les absences non justifiées signalées après 48h déclenchent une alerte

**Dépendances:** US-INS-001

---

#### US-ACA-008: Justification des absences
**Priorité:** 🟡 Haute  
**Story Points:** 3

**En tant que** Parent  
**Je veux** justifier l'absence de mon enfant  
**Afin qu'elle ne soit pas comptée comme non justifiée

**Critères d'acceptation:**
- [ ] Je peux voir les absences de mon enfant
- [ ] Je peux justifier une absence
- [ ] Je peux fournir un motif
- [ ] Je peux uploader un justificatif (certificat médical, etc.)
- [ ] Le système met à jour le statut de l'absence
- [ ] L'enseignant est notifié

**Dépendances:** US-ACA-007

---

### Epic: Gestion des Emplois du Temps

#### US-ACA-009: Création de l'emploi du temps
**Priorité:** 🔴 Critique  
**Story Points:** 13

**En tant que** Directeur / Secrétaire  
**Je veux** créer l'emploi du temps de l'école  
**Afin d'organiser les cours

**Critères d'acceptation:**
- [ ] Je peux créer un emploi du temps pour une classe
- [ ] Je peux ajouter des créneaux horaires
- [ ] Je peux assigner des cours aux créneaux
- [ ] Je peux définir la matière, l'enseignant, la salle
- [ ] Le système détecte les conflits (enseignant, salle)
- [ ] Le système empêche les conflits
- [ ] Je peux voir l'emploi du temps sous forme de grille
- [ ] Je peux modifier l'emploi du temps
- [ ] Je peux dupliquer un emploi du temps
- [ ] Je peux exporter l'emploi du temps en PDF
- [ ] L'emploi du temps est accessible aux enseignants et élèves

**Dépendances:** US-ACA-002, US-INS-007

---

## 💰 MODULE FINANCIER (FINANCIAL)

### Epic: Gestion des Frais

#### US-FIN-001: Configuration des frais de scolarité
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Comptable / Directeur  
**Je veux** configurer les frais de scolarité  
**Afin de définir ce que les familles doivent payer

**Critères d'acceptation:**
- [ ] Je peux créer des types de frais (inscription, scolarité, cantine, transport, etc.)
- [ ] Je peux définir le montant de chaque frais
- [ ] Je peux définir la périodicité (mensuel, trimestriel, annuel)
- [ ] Je peux définir les échéances de paiement
- [ ] Je peux appliquer des remises (bourses, fratrie, etc.)
- [ ] Je peux configurer des frais par niveau
- [ ] Je peux activer/désactiver un frais
- [ ] Les modifications sont journalisées

**Dépendances:** US-TEN-002

---

#### US-FIN-002: Génération des factures
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Comptable  
**Je veux** générer des factures pour les élèves  
**Afin de demander le paiement des frais

**Critères d'acceptation:**
- [ ] Je peux générer une facture pour un élève
- [ ] Je peux générer des factures pour une classe entière
- [ ] Je peux générer des factures pour tous les élèves
- [ ] Le système calcule automatiquement le total
- [ ] Le système applique les remises automatiquement
- [ ] La facture contient le détail des frais
- [ ] La facture contient les échéances
- [ ] La facture est générée en PDF
- [ ] La facture est envoyée par email aux parents
- [ ] Je peux régénérer une facture

**Dépendances:** US-FIN-001, US-INS-001

---

#### US-FIN-003: Enregistrement des paiements
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Comptable  
**Je veux** enregistrer les paiements des familles  
**Afin de suivre les règlements

**Critères d'acceptation:**
- [ ] Je peux enregistrer un paiement pour une facture
- [ ] Je peux définir le mode de paiement (espèces, chèque, virement, mobile money)
- [ ] Je peux définir la référence du paiement
- [ ] Je peux enregistrer un paiement partiel
- [ ] Le système met à jour le solde de la facture
- [ ] Le système génère un reçu
- [ ] Le reçu est envoyé par email
- [ ] Je peux annuler un paiement
- [ ] Les paiements ne peuvent pas être supprimés

**Dépendances:** US-FIN-002

---

#### US-FIN-004: Suivi des impayés
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Comptable / Directeur  
**Je veux** voir les impayés  
**Afin de relancer les familles

**Critères d'acceptation:**
- [ ] Je peux voir la liste des factures impayées
- [ ] Je peux filtrer par échéance dépassée
- [ ] Je peux voir le montant total des impayés
- [ ] Je peux voir les détails de chaque facture
- [ ] Je peux envoyer des rappels par email
- [ ] Je peux envoyer des rappels par SMS
- [ ] Je peux voir l'historique des relances

**Dépendances:** US-FIN-002, US-FIN-003

---

#### US-FIN-005: Gestion des remises
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Directeur / Comptable  
**Je veux** gérer les remises  
**Afin d'accorder des réductions

**Critères d'acceptation:**
- [ ] Je peux créer des types de remises (bourse, fratrie, mérite, etc.)
- [ ] Je peux définir le pourcentage ou le montant de la remise
- [ ] Je peux appliquer une remise à un élève
- [ ] Je peux définir la durée de la remise
- [ ] Je peux modifier une remise
- [ ] Je peux supprimer une remise
- [ ] Les remises sont appliquées automatiquement sur les factures

**Dépendances:** US-FIN-001

---

#### US-FIN-006: Rapports financiers
**Priorité:** 🟡 Haute  
**Story Points:** 8

**En tant que** Comptable / Directeur  
**Je veux** générer des rapports financiers  
**Afin d'analyser la situation financière

**Critères d'acceptation:**
- [ ] Je peux générer un rapport des recettes
- [ ] Je peux générer un rapport des dépenses
- [ ] Je peux générer un rapport par période
- [ ] Je peux générer un rapport par type de frais
- [ ] Je peux générer un rapport par classe
- [ ] Je peux exporter les rapports en Excel
- [ ] Je peux exporter les rapports en PDF
- [ ] Les rapports contiennent des graphiques

**Dépendances:** US-FIN-002, US-FIN-003

---

## 👥 MODULE RH (HUMAN RESOURCES)

### Epic: Gestion des Enseignants

#### US-HR-001: Création d'un enseignant
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Directeur / RH  
**Je veux** créer un profil d'enseignant  
**Afin de l'ajouter à l'école

**Critères d'acceptation:**
- [ ] Je peux saisir les informations personnelles (nom, prénom, date de naissance)
- [ ] Je peux saisir les informations de contact (email, téléphone, adresse)
- [ ] Je peux définir la spécialisation
- [ ] Je peux définir les matières enseignées
- [ ] Je peux uploader une photo
- [ ] Je peux définir la date d'embauche
- [ ] Je peux définir le type de contrat
- [ ] Le système génère un numéro d'employé unique
- [ ] Le système crée automatiquement un compte utilisateur
- [ ] Je reçois une confirmation de création

**Dépendances:** US-TEN-002, US-SEC-001

---

#### US-HR-002: Gestion des contrats
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** RH  
**Je veux** gérer les contrats des enseignants  
**Afin de suivre les engagements

**Critères d'acceptation:**
- [ ] Je peux créer un contrat pour un enseignant
- [ ] Je peux définir le type de contrat (CDI, CDD, vacataire)
- [ ] Je peux définir la durée du contrat
- [ ] Je peux définir le salaire
- [ ] Je peux uploader le contrat signé
- [ ] Je peux voir les contrats en cours
- [ ] Je peux voir les contrats expirant bientôt
- [ ] Le système notifie avant l'expiration du contrat

**Dépendances:** US-HR-001

---

#### US-HR-003: Gestion des congés
**Priorité:** 🟡 Haute  
**Story Points:** 8

**En tant que** Enseignant / RH  
**Je veux** gérer les congés  
**Afin de planifier les absences

**Critères d'acceptation:**
- [ ] Je peux demander un congé
- [ ] Je peux définir les dates du congé
- [ ] Je peux définir le type de congé (annuel, maladie, sans solde)
- [ ] Je peux ajouter un commentaire
- [ ] Le système vérifie le solde de congés
- [ ] Le RH peut approuver ou refuser le congé
- [ ] Le RH peut voir la liste des demandes
- [ ] L'enseignant est notifié de la décision
- [ ] Le système met à jour le solde de congés

**Dépendances:** US-HR-001

---

#### US-HR-004: Consultation du profil enseignant
**Priorité:** 🟡 Haute  
**Story Points:** 3

**En tant que** Directeur / RH / Enseignant  
**Je veux** consulter le profil d'un enseignant  
**Afin de voir ses informations

**Critères d'acceptation:**
- [ ] Je peux voir les informations personnelles
- [ ] Je peux voir les spécialisations
- [ ] Je peux voir les matières enseignées
- [ ] Je peux voir les classes assignées
- [ ] Je peux voir le contrat
- [ ] Je peux voir l'historique des congés
- [ ] L'accès est restreint selon mes permissions

**Dépendances:** US-HR-001

---

### Epic: Gestion du Personnel Administratif

#### US-HR-005: Création d'un membre du personnel
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Directeur / RH  
**Je veux** créer un profil de personnel administratif  
**Afin de l'ajouter à l'école

**Critères d'acceptation:**
- [ ] Je peux saisir les informations personnelles
- [ ] Je peux définir le poste (secrétaire, comptable, surveillant, etc.)
- [ ] Je peux définir le département
- [ ] Je peux définir les permissions
- [ ] Le système génère un numéro d'employé unique
- [ ] Le système crée automatiquement un compte utilisateur

**Dépendances:** US-TEN-002, US-SEC-001

---

## 📢 MODULE COMMUNICATION

### Epic: Messagerie Interne

#### US-COM-001: Envoi de messages
**Priorité:** 🟡 Haute  
**Story Points:** 8

**En tant que** Utilisateur  
**Je veux** envoyer des messages internes  
**Afin de communiquer avec les autres membres

**Critères d'acceptation:**
- [ ] Je peux envoyer un message à un utilisateur
- [ ] Je peux envoyer un message à un groupe
- [ ] Je peux envoyer un message à une classe
- [ ] Je peux joindre des fichiers
- [ ] Je peux voir l'historique de conversation
- [ ] Je peux voir si le message a été lu
- [ ] Je reçois une notification pour les nouveaux messages
- [ ] Les messages sont triés par conversation

**Dépendances:** US-SEC-002

---

#### US-COM-002: Notifications
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Utilisateur  
**Je veux** recevoir des notifications  
**Afin d'être informé des événements importants

**Critères d'acceptation:**
- [ ] Je reçois une notification pour les nouveaux messages
- [ ] Je reçois une notification pour les nouvelles notes
- [ ] Je reçois une notification pour les nouvelles absences
- [ ] Je reçois une notification pour les nouvelles factures
- [ ] Je peux voir la liste des notifications
- [ ] Je peux marquer une notification comme lue
- [ ] Je peux configurer mes préférences de notification

**Dépendances:** US-COM-001

---

### Epic: Annonces et Circulaires

#### US-COM-003: Publication d'annonces
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Directeur / Secrétaire  
**Je veux** publier des annonces  
**Afin d'informer la communauté

**Critères d'acceptation:**
- [ ] Je peux créer une annonce
- [ ] Je peux définir le titre et le contenu
- [ ] Je peux définir la cible (tous, enseignants, parents, élèves)
- [ ] Je peux joindre des fichiers
- [ ] Je peux définir une date de publication
- [ ] Je peux définir une date d'expiration
- [ ] L'annonce est visible par la cible
- [ ] Les utilisateurs reçoivent une notification

**Dépendances:** US-SEC-002

---

#### US-COM-004: Consultation des annonces
**Priorité:** 🟢 Moyenne  
**Story Points:** 3

**En tant que** Utilisateur  
**Je veux** consulter les annonces  
**Afin d'être informé

**Critères d'acceptation:**
- [ ] Je peux voir la liste des annonces
- [ ] Je peux filtrer par cible
- [ ] Je peux voir le détail d'une annonce
- [ ] Je peux voir les fichiers joints
- [ ] Les annonces expirées ne sont plus visibles

**Dépendances:** US-COM-003

---

## 🌐 MODULE FRONTEND (ANGULAR)

### Epic: Interface Utilisateur

#### US-FE-001: Dashboard
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Utilisateur  
**Je veux** voir un dashboard personnalisé  
**Afin d'avoir une vue d'ensemble

**Critères d'acceptation:**
- [ ] Je vois les statistiques pertinentes selon mon rôle
- [ ] Je vois les notifications récentes
- [ ] Je vois les tâches à faire
- [ ] Je vois les événements à venir
- [ ] Je peux personnaliser mon dashboard
- [ ] L'interface est responsive (mobile, tablette, desktop)
- [ ] L'interface utilise les couleurs du tenant

**Dépendances:** US-SEC-002

---

#### US-FE-002: Gestion des élèves
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Secrétaire  
**Je veux** gérer les élèves via l'interface  
**Afin de faciliter les opérations

**Critères d'acceptation:**
- [ ] Je peux voir la liste des élèves
- [ ] Je peux rechercher un élève
- [ ] Je peux filtrer par classe
- [ ] Je peux créer un nouvel élève
- [ ] Je peux modifier un élève
- [ ] Je peux voir le détail d'un élève
- [ ] Je peux imprimer le dossier d'un élève
- [ ] L'interface est intuitive et rapide

**Dépendances:** US-INS-001, US-FE-001

---

#### US-FE-003: Gestion des notes
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Enseignant  
**Je veux** gérer les notes via l'interface  
**Afin de faciliter la saisie

**Critères d'acceptation:**
- [ ] Je peux voir la liste de mes classes
- [ ] Je peux sélectionner une classe et une matière
- [ ] Je peux saisir les notes dans un tableau
- [ ] Le système calcule automatiquement les moyennes
- [ ] Je peux sauvegarder les notes
- [ ] Je peux voir l'historique des modifications
- [ ] Je peux exporter les notes en Excel

**Dépendances:** US-ACA-003, US-FE-001

---

#### US-FE-004: Espace Parent
**Priorité:** 🔴 Critique  
**Story Points:** 8

**En tant que** Parent  
**Je veux** avoir un espace dédié  
**Afin de suivre mon enfant

**Critères d'acceptation:**
- [ ] Je peux voir les informations de mon enfant
- [ ] Je peux voir les notes
- [ ] Je peux voir les absences
- [ ] Je peux voir les bulletins
- [ ] Je peux voir les factures
- [ ] Je peux voir les annonces
- [ ] Je peux contacter l'école
- [ ] L'interface est simple et accessible

**Dépendances:** US-INS-005, US-ACA-005, US-FIN-002, US-FE-001

---

## 📊 MODULE RAPPORTS

### Epic: Rapports et Statistiques

#### US-RPT-001: Rapport des effectifs
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Directeur  
**Je veux** générer un rapport des effectifs  
**Afin de connaître le nombre d'élèves

**Critères d'acceptation:**
- [ ] Je peux voir le nombre total d'élèves
- [ ] Je peux voir le nombre par classe
- [ ] Je peux voir le nombre par niveau
- [ ] Je peux voir le nombre par sexe
- [ ] Je peux voir l'évolution dans le temps
- [ ] Je peux exporter le rapport

**Dépendances:** US-INS-001

---

#### US-RPT-002: Rapport des résultats
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Directeur  
**Je veux** générer un rapport des résultats  
**Afin d'analyser la performance

**Critères d'acceptation:**
- [ ] Je peux voir la moyenne générale de l'école
- [ ] Je peux voir la moyenne par classe
- [ ] Je peux voir la moyenne par matière
- [ ] Je peux voir le taux de réussite
- [ ] Je peux voir le classement
- [ ] Je peux exporter le rapport

**Dépendances:** US-ACA-003

---

#### US-RPT-003: Rapport financier
**Priorité:** 🟡 Haute  
**Story Points:** 5

**En tant que** Directeur / Comptable  
**Je veux** générer un rapport financier  
**Afin d'analyser la situation

**Critères d'acceptation:**
- [ ] Je peux voir le total des recettes
- [ ] Je peux voir le total des impayés
- [ ] Je peux voir le taux de recouvrement
- [ ] Je peux voir l'évolution dans le temps
- [ ] Je peux exporter le rapport

**Dépendances:** US-FIN-003

---

## 🎯 Priorité des Sprints

### Sprint 1 (4 semaines) - Fondation
- US-TEN-001: Création d'un nouveau tenant
- US-TEN-002: Activation d'un tenant
- US-SEC-001: Inscription d'un super admin
- US-SEC-002: Connexion multi-tenant
- US-SEC-003: Rafraîchissement du token
- US-SEC-004: Déconnexion

### Sprint 2 (4 semaines) - Gestion de base
- US-TEN-005: Configuration du tenant
- US-TEN-006: Invitation d'utilisateurs
- US-SEC-005: Gestion des rôles et permissions
- US-INS-001: Inscription d'un nouvel élève
- US-INS-007: Création d'une classe

### Sprint 3 (4 semaines) - Académique
- US-ACA-001: Création d'une matière
- US-ACA-002: Assignation d'enseignants aux matières
- US-ACA-003: Saisie des notes
- US-ACA-005: Consultation des notes
- US-ACA-006: Génération du bulletin

### Sprint 4 (4 semaines) - Financier
- US-FIN-001: Configuration des frais de scolarité
- US-FIN-002: Génération des factures
- US-FIN-003: Enregistrement des paiements
- US-FIN-004: Suivi des impayés

### Sprint 5 (4 semaines) - Communication & RH
- US-HR-001: Création d'un enseignant
- US-COM-001: Envoi de messages
- US-COM-003: Publication d'annonces
- US-ACA-009: Création de l'emploi du temps

### Sprint 6 (4 semaines) - Frontend
- US-FE-001: Dashboard
- US-FE-002: Gestion des élèves
- US-FE-003: Gestion des notes
- US-FE-004: Espace Parent

### Sprint 7 (4 semaines) - Rapports & Optimisation
- US-RPT-001: Rapport des effectifs
- US-RPT-002: Rapport des résultats
- US-RPT-003: Rapport financier
- Optimisations et corrections

---

## 📝 Notes pour l'équipe

- Chaque user story doit être estimée en story points lors du sprint planning
- Les critères d'acceptation doivent être testables
- Les dépendances doivent être respectées
- Les stories peuvent être divisées si elles sont trop grandes
- Les stories peuvent être ajoutées ou modifiées selon les besoins du client
- Les tests doivent être écrits pour chaque critère d'acceptation
