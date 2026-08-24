package com.example.data.repository

import com.example.data.model.*

object BookData {

    val cahiers = listOf(
        Cahier(
            id = 1,
            number = "01",
            title = "Mon argent, mes choix",
            theme = "Éducation financière",
            subtitle = "Comprendre, choisir, épargner : les bases de l'éducation financière expliquées simplement, avec des exemples de la vie de tous les jours.",
            summary = "Budget, épargne, intérêts composés, crédit, inflation, prix et arnaques commerciales. Monnaie : l'ariary (Ar).",
            pageStart = 3,
            totalPlanches = 20,
            colorHex = 0xFF681923,
            iconName = "payments"
        ),
        Cahier(
            id = 2,
            number = "02",
            title = "Vie & psychologie",
            theme = "Comprendre ses émotions",
            subtitle = "Comprendre ses émotions, tenir face au stress, dire non, se relever, choisir son entourage : les bases pour grandir bien dans sa tête.",
            summary = "Émotions, stress, dire non, échec, confiance, manipulation, conflits, entourage, discipline, sommeil et écrans.",
            pageStart = 23,
            totalPlanches = 20,
            colorHex = 0xFF6F783A,
            iconName = "psychology"
        ),
        Cahier(
            id = 3,
            number = "03",
            title = "Travail & carrière",
            theme = "Trouver réellement un emploi",
            subtitle = "Comment trouver réellement un emploi : CV, portfolio, entretien, prix, négociation, réseau, équipe — et transformer ce qu'on sait faire en revenu.",
            summary = "CV, portfolio, méthode STAR, entretien, pitch 30s, devis, tarification, négociation client, personal branding, travail d'équipe.",
            pageStart = 43,
            totalPlanches = 20,
            colorHex = 0xFFD6A12A,
            iconName = "work"
        ),
        Cahier(
            id = 4,
            number = "04",
            title = "Entrepreneuriat",
            theme = "Construire une activité",
            subtitle = "Trouver un vrai problème, tester avant de dépenser, calculer ses coûts, vendre, fidéliser et lire ses chiffres : construire une activité qui tient debout.",
            summary = "Problème, client type, produit minimum (MVP), coût de revient, marge, entonnoir de vente, fidélisation, trésorerie, gestion.",
            pageStart = 63,
            totalPlanches = 20,
            colorHex = 0xFF56602B,
            iconName = "storefront"
        ),
        Cahier(
            id = 5,
            number = "05",
            title = "Communication",
            theme = "Se faire comprendre",
            subtitle = "Parler devant des gens, présenter une idée, écrire juste, écouter vraiment, poser les bonnes questions et encaisser une critique : les gestes qui se travaillent.",
            summary = "Écoute active, questions ouvertes, prise de parole, corps et voix, analogie, message pro, négociation, gérer la critique, malentendus.",
            pageStart = 83,
            totalPlanches = 20,
            colorHex = 0xFF4D111A,
            iconName = "record_voice_over"
        ),
        Cahier(
            id = 6,
            number = "06",
            title = "Vie quotidienne",
            theme = "Se débrouiller dans le réel",
            subtitle = "Lire un contrat, faire ses papiers, organiser son temps, cuisiner, entretenir ses affaires, éviter les arnaques et vérifier une information.",
            summary = "Contrats, droits & devoirs, démarches administratives, assurance, matrice du temps, choix importants, cuisine simple, entretien & réparation.",
            pageStart = 103,
            totalPlanches = 20,
            colorHex = 0xFF6F783A,
            iconName = "home"
        ),
        Cahier(
            id = 7,
            number = "07",
            title = "À l'ère numérique",
            theme = "Comprendre et se protéger",
            subtitle = "Protéger ses comptes, repérer une arnaque, comprendre l'IA et les algorithmes, chercher efficacement et distinguer l'info de la publicité.",
            summary = "Double vérification, mots de passe robustes, phishing, que faire en cas de piratage, recherche experte, limites de l'IA, équilibre d'écran.",
            pageStart = 123,
            totalPlanches = 20,
            colorHex = 0xFF681923,
            iconName = "devices"
        ),
        Cahier(
            id = 8,
            number = "08",
            title = "Les compétences essentielles",
            theme = "Apprendre, décider, avancer",
            subtitle = "Apprendre à apprendre, chercher juste, résoudre l'inconnu, décider dans le flou, recommencer, transformer une idée en projet, devenir autonome.",
            summary = "Rappel actif, répétition espacée, problème inconnu, découpage et modélisation, coût de l'indécision, rebond après échec, boussole sur 10 ans.",
            pageStart = 143,
            totalPlanches = 20,
            colorHex = 0xFFD6A12A,
            iconName = "explore"
        )
    )

    fun getPlanchesForCahier(cahierId: Int): List<Planche> {
        return allPlanches.filter { it.cahierId == cahierId }
    }

    val allPlanches: List<Planche> = listOf(
        // ================= CAHIER 01: MON ARGENT, MES CHOIX =================
        Planche(
            id = "c1_p01",
            cahierId = 1,
            pageNumber = 1,
            sectionNumber = "01 NOTION · COMPRENDRE",
            title = "L'argent, c'est quoi ?",
            accroche = "L'argent est un outil d'échange. Avant lui, on troquait : trois poules contre un sac de riz. Mais si le voisin ne veut pas de poules ? L'argent règle ce problème : tout le monde l'accepte, il se conserve et il permet de mesurer la valeur des choses.",
            schema = SchemaData(
                type = SchemaType.FLOW_STEPS,
                title = "L'évolution de l'échange",
                items = listOf(
                    SchemaItem("1", "LE TROC", "On échange un objet contre un autre."),
                    SchemaItem("2", "LES PIÈCES", "Le métal a une valeur reconnue par tous."),
                    SchemaItem("3", "LES BILLETS", "Du papier qui vaut ce que l'État garantit."),
                    SchemaItem("4", "LE NUMÉRIQUE", "Mobile money, cartes : l'argent devient invisible.")
                )
            ),
            keyCards = listOf(
                KeyCard("F.1", "ÉCHANGER", "On l'accepte partout pour acheter et vendre."),
                KeyCard("F.2", "MESURER", "Il donne un prix : 1 kg de riz = 3 000 Ar."),
                KeyCard("F.3", "CONSERVER", "Il garde sa valeur pour plus tard, si on l'épargne.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.LE_SAIS_TU,
                title = "LE SAIS-TU ?",
                content = "L'argent ne crée pas de richesse tout seul : il représente le travail, le temps ou les ressources qu'on a échangés. Perdre de l'argent, c'est perdre le temps qu'il a fallu pour le gagner — d'où l'intérêt de le dépenser avec réflexion."
            )
        ),
        Planche(
            id = "c1_p02",
            cahierId = 1,
            pageNumber = 2,
            sectionNumber = "02 NOTION · COMPRENDRE",
            title = "D'où vient l'argent ?",
            accroche = "L'argent qui entre s'appelle un revenu. Il ne tombe pas du ciel : il récompense un travail, une idée ou un risque. Pour un élève, les sources sont plus modestes, mais le principe est le même.",
            keyCards = listOf(
                KeyCard("R.1", "SALAIRE", "Payé par un employeur en échange de son temps et de ses compétences."),
                KeyCard("R.2", "ENTREPRISE", "Vendre un produit ou un service : le bénéfice est le revenu."),
                KeyCard("R.3", "PLACEMENT", "L'argent épargné rapporte des intérêts ou des loyers."),
                KeyCard("R.4", "AIDE", "Bourse, soutien familial : précieux, mais pas garanti.")
            ),
            detailedBoxes = listOf(
                DetailedBox(
                    title = "EXEMPLE — UN MOIS DE RAKOTO, 15 ANS",
                    items = listOf(
                        "Argent de poche : 12 000 Ar",
                        "Cours particuliers au voisin : 6 000 Ar",
                        "Vente de deux vieux livres : 2 000 Ar",
                        "TOTAL REVENUS = 20 000 Ar"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "À RETENIR",
                content = "Plus tes sources de revenus sont variées, moins tu dépends d'une seule. Et un revenu, même petit, mérite un budget : c'est l'habitude qui compte, pas le montant."
            )
        ),
        Planche(
            id = "c1_p03",
            cahierId = 1,
            pageNumber = 3,
            sectionNumber = "03 NOTION · CHOISIR",
            title = "Besoins ou envies ?",
            accroche = "Avant de dépenser, une seule question : est-ce que j'en ai besoin, ou est-ce que j'en ai envie ? Les deux sont légitimes — mais les besoins passent toujours en premier.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "BESOINS — INDISPENSABLES",
                    items = listOf(
                        "Nourriture et eau potable",
                        "Logement, électricité",
                        "Frais de scolarité, fournitures",
                        "Transport pour aller en classe",
                        "Santé, médicaments"
                    ),
                    highlightColor = 0xFF681923
                ),
                DetailedBox(
                    title = "ENVIES — AGRÉABLES, MAIS OPTIONNELLES",
                    items = listOf(
                        "Nouveau téléphone à la mode",
                        "Snacks et sodas tous les jours",
                        "Vêtements de marque",
                        "Crédit pour les jeux mobiles",
                        "Sortie chaque week-end"
                    ),
                    highlightColor = 0xFFD6A12A
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.CONSEIL_SEMAINE,
                title = "AVANT D'ACHETER, POSE-TOI 3 QUESTIONS",
                content = "1. Est-ce que je peux m'en passer une semaine ?\n2. Est-ce que j'ai comparé le prix ailleurs ?\n3. Est-ce que cet achat m'éloigne d'un objectif plus important ?"
            )
        ),
        Planche(
            id = "c1_p04",
            cahierId = 1,
            pageNumber = 4,
            sectionNumber = "04 NOTION · PLANIFIER",
            title = "Le budget : 50 · 30 · 20",
            accroche = "Un budget, c'est un plan pour ton argent. Une règle simple pour commencer : 50 % pour les besoins, 30 % pour les envies, 20 % pour l'épargne. Ce que tu reçois se répartit ainsi.",
            schema = SchemaData(
                type = SchemaType.COMPARISON,
                title = "Répartition 50 · 30 · 20 sur 20 000 Ar",
                comparisonLeft = Pair("BESOINS (50%)", "10 000 Ar : Transport, fournitures, repas."),
                comparisonRight = Pair("ENVIES (30%) & ÉPARGNE (20%)", "6 000 Ar (Envies) + 4 000 Ar (Épargne mise de côté d'abord !)")
            ),
            detailedBoxes = listOf(
                DetailedBox(
                    title = "MON BUDGET DU MOIS — EXEMPLE",
                    items = listOf(
                        "Argent reçu (poche + petit boulot) : + 20 000 Ar",
                        "Transport (taxi-be) et fournitures : − 7 500 Ar",
                        "Repas à l'école : − 2 500 Ar",
                        "Sorties, snacks : − 5 000 Ar",
                        "Épargne mise de côté : − 4 000 Ar",
                        "Reste disponible : = 1 000 Ar"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.REGLE_DOR,
                title = "LA RÈGLE D'OR",
                content = "Note chaque dépense pendant une semaine, même 200 Ar. Tu seras surpris de voir où part l'argent — c'est la première étape pour le maîtriser."
            )
        ),
        Planche(
            id = "c1_p05",
            cahierId = 1,
            pageNumber = 5,
            sectionNumber = "05 NOTION · PLANIFIER",
            title = "Le journal des dépenses",
            accroche = "Le budget est un plan ; le journal, c'est la réalité. Pendant une semaine, note chaque sortie d'argent. À la fin, compare : c'est là que se cachent les surprises.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "CE QUE LE JOURNAL RÉVÈLE",
                    items = listOf(
                        "Les snacks (6 500 Ar) coûtent plus cher que le transport (3 000 Ar).",
                        "Sans journal, personne ne l'aurait deviné.",
                        "Un seul changement — un snack sur deux — libère 3 000 Ar par semaine, soit 12 000 Ar par mois.",
                        "Le samedi coûte à lui seul plus d'un tiers de la semaine (6 000 Ar)."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.CONSEIL_SEMAINE,
                title = "OUTIL PRATIQUE",
                content = "Un petit carnet, l'application de notes du téléphone ou une feuille pliée dans la poche : peu importe. La règle : noter tout de suite, sinon on oublie."
            )
        ),
        Planche(
            id = "c1_p06",
            cahierId = 1,
            pageNumber = 6,
            sectionNumber = "06 NOTION · GRANDIR",
            title = "L'épargne, une graine",
            accroche = "Épargner, c'est se payer soi-même en premier. Une petite somme, régulièrement, vaut mieux qu'une grosse somme une seule fois. Ce que tu mets de côté aujourd'hui, c'est de la liberté pour demain.",
            schema = SchemaData(
                type = SchemaType.STAIRS_PROGRESSION,
                title = "1 000 Ar par semaine, sans intérêts, ça pousse déjà !",
                items = listOf(
                    SchemaItem("Semaine 1", "1 000 Ar", "Le premier pas"),
                    SchemaItem("Mois 1", "4 000 Ar", "L'habitude s'installe"),
                    SchemaItem("Mois 6", "24 000 Ar", "Un vrai matelas"),
                    SchemaItem("An 1", "48 000 Ar", "Autonomie et sécurité")
                )
            ),
            detailedBoxes = listOf(
                DetailedBox(
                    title = "MAUVAISE vs BONNE HABITUDE",
                    items = listOf(
                        "❌ Mauvaise : Je reçois 20 000 Ar → Je dépense → J'épargne ce qui reste → Souvent 0 Ar.",
                        "✅ Bonne : Je reçois 20 000 Ar → Je mets 4 000 Ar de côté → Je vis avec 16 000 Ar → Épargne garantie !"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.CONSEIL_SEMAINE,
                title = "MÉTHODE",
                content = "Dès que tu reçois de l'argent, mets 20 % de côté immédiatement — dans une boîte, un compte ou un mobile wallet séparé. Ce que tu ne vois pas, tu ne le dépenses pas."
            )
        ),
        Planche(
            id = "c1_p07",
            cahierId = 1,
            pageNumber = 7,
            sectionNumber = "07 NOTION · GRANDIR",
            title = "Les intérêts composés",
            accroche = "Quand l'argent épargné est placé, il rapporte des intérêts. Et l'année suivante, ces intérêts rapportent eux aussi des intérêts. C'est une boule de neige : lente au début, impressionnante à la fin.",
            keyCards = listOf(
                KeyCard("72÷T", "LA RÈGLE DE 72", "72 ÷ taux d'intérêt = nombre d'années nécessaires pour doubler son capital."),
                KeyCard("4%", "À 4 %", "L'argent double en 18 ans."),
                KeyCard("6%", "À 6 %", "50 000 Ar deviennent 100 000 Ar en 12 ans, puis 200 000 Ar en 24 ans sans rien ajouter."),
                KeyCard("12%", "À 12 %", "L'argent double en seulement 6 ans.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "LE TEMPS EST TON MEILLEUR ALLIÉ",
                content = "Commencer à 15 ans plutôt qu'à 25 ans, ce n'est pas 10 ans de plus : c'est souvent le double du résultat final. L'important n'est pas d'attendre d'avoir beaucoup, c'est de commencer."
            )
        ),
        Planche(
            id = "c1_p08",
            cahierId = 1,
            pageNumber = 8,
            sectionNumber = "08 NOTION · VISER",
            title = "Un objectif, un plan",
            accroche = "« Je veux économiser » ne suffit pas. Un bon objectif est précis, chiffré et daté. Exemple : acheter une calculatrice scientifique à 60 000 Ar avant la rentrée, dans 6 mois.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "MÉTHODE S.M.A.R.T.",
                    items = listOf(
                        "S - Spécifique : Quoi exactement ? Une calculatrice, pas 'des trucs'.",
                        "M - Mesurable : Combien ? 60 000 Ar.",
                        "A - Atteignable : 10 000 Ar / mois, c'est possible avec mon budget.",
                        "R - Réaliste : Le prix a été vérifié en boutique.",
                        "T - Temporel : Quand ? Avant la rentrée, dans 6 mois."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.EXERCICE_PRATIQUE,
                title = "À TOI DE JOUER",
                content = "Écris ton propre objectif SMART sur une feuille et affiche-le là où tu le verras chaque jour. À chaque versement, colorie une case : voir sa progression donne envie de continuer."
            )
        ),
        Planche(
            id = "c1_p09",
            cahierId = 1,
            pageNumber = 9,
            sectionNumber = "09 NOTION · SE PROTÉGER",
            title = "La banque et le mobile money",
            accroche = "Garder ses billets sous le matelas, c'est risquer le vol, l'oubli… ou la tentation. Un compte bancaire ou un porte-monnaie mobile protège l'argent, permet de le faire circuler et laisse une trace de chaque opération.",
            keyCards = listOf(
                KeyCard("B.1", "DÉPÔT", "Mettre de l'argent sur le compte. Le solde augmente."),
                KeyCard("B.2", "RETRAIT", "Reprendre de l'argent en espèces. Le solde baisse."),
                KeyCard("B.3", "FRAIS", "Certaines opérations coûtent : renseigne-toi avant."),
                KeyCard("B.4", "RELEVÉ", "La liste de tous les mouvements. À vérifier chaque mois.")
            ),
            detailedBoxes = listOf(
                DetailedBox(
                    title = "SÉCURITÉ — 4 RÈGLES QUI NE SE DISCUTENT PAS",
                    items = listOf(
                        "Ton code secret ne se partage avec personne, même un ami proche.",
                        "Vérifie le numéro et le montant deux fois avant de valider un transfert.",
                        "Aucune banque ne demande ton code par SMS ou par appel.",
                        "Garde ou photographie chaque reçu de transaction."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.BON_REFLEXE,
                title = "BON RÉFLEXE",
                content = "Un compte séparé pour l'épargne, que tu ne consultes qu'une fois par mois, est le meilleur moyen de ne pas y toucher. La distance protège l'objectif."
            )
        ),
        Planche(
            id = "c1_p10",
            cahierId = 1,
            pageNumber = 10,
            sectionNumber = "10 NOTION · SE PROTÉGER",
            title = "Le crédit et la dette",
            accroche = "Emprunter, c'est utiliser aujourd'hui l'argent de demain — et le rendre avec un supplément : les intérêts. Le crédit peut être utile pour un vrai projet. Il devient dangereux quand il finance des envies.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "COMBIEN COÛTE VRAIMENT UN PRÊT ?",
                    items = listOf(
                        "100 000 Ar empruntés à 3 % d'intérêt par mois remboursé en 12 mois.",
                        "Capital : 100 000 Ar | Intérêts : 36 000 Ar.",
                        "Total remboursé : 136 000 Ar — soit 11 333 Ar par mois pendant un an.",
                        "Les 36 000 Ar d'intérêts, c'est le prix du temps."
                    )
                ),
                DetailedBox(
                    title = "CRÉDIT UTILE vs CRÉDIT PIÈGE",
                    items = listOf(
                        "✅ Utile : Finance quelque chose qui garde ou crée de la valeur (études, outil).",
                        "❌ Piège : Finance une envie qui perd vite sa valeur (téléphone, vêtements), ou emprunter pour rembourser un autre emprunt."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.CONSEIL_SEMAINE,
                title = "LA QUESTION À SE POSER",
                content = "« Est-ce que je pourrais épargner pour l'acheter dans trois mois ? » Si oui, l'épargne coûte 0 Ar d'intérêts et le crédit en coûte des milliers. Le choix est vite fait."
            )
        ),
        Planche(
            id = "c1_p11",
            cahierId = 1,
            pageNumber = 11,
            sectionNumber = "11 NOTION · SE PROTÉGER",
            title = "Les pièges à éviter",
            accroche = "Savoir gérer son argent, c'est aussi savoir dire non. Voici quatre pièges fréquents et le réflexe à adopter face à chacun.",
            keyCards = listOf(
                KeyCard("1", "LA DETTE FACILE", "« Je te prête, tu me rends plus tard. » Réflexe : n'emprunte que pour un vrai besoin, et note la date."),
                KeyCard("2", "LA PUBLICITÉ", "Les promos 'limitées' poussent à acheter vite. Réflexe : attends 24 h avant tout achat non prévu."),
                KeyCard("3", "LES ARNAQUES", "« Envoie 5 000 Ar et gagne 50 000 Ar ! » Réflexe : ne transfère jamais d'argent à un inconnu."),
                KeyCard("4", "LES PETITES DÉPENSES", "500 Ar de snacks/jour = 180 000 Ar par an. Réflexe : compte-les une semaine et décide.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.SIGNAL_ALERTE,
                title = "EN CAS DE DOUTE",
                content = "Une bonne décision financière supporte toujours d'attendre un jour et d'être expliquée à quelqu'un de confiance. Si tu dois te cacher ou te dépêcher, c'est un signal d'alerte."
            )
        ),
        Planche(
            id = "c1_p12",
            cahierId = 1,
            pageNumber = 12,
            sectionNumber = "12 NOTION · SE PROTÉGER",
            title = "Comparer avant d'acheter",
            accroche = "Le même produit n'a pas le même prix partout, ni dans tous les formats. Deux outils suffisent pour ne plus se faire avoir : le prix unitaire et le vrai calcul d'une promotion.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "OUTIL 1 — LE PRIX AU LITRE (OU AU KILO)",
                    items = listOf(
                        "Petite bouteille (0.5 L · 1 500 Ar) = 3 000 Ar / L",
                        "Bouteille moyenne (1.0 L · 2 600 Ar) = 2 600 Ar / L",
                        "Grande bouteille (1.5 L · 3 600 Ar) = 2 400 Ar / L",
                        "Le grand format est le moins cher au litre — si tu le consommes vraiment sans gaspillage."
                    )
                ),
                DetailedBox(
                    title = "OUTIL 2 — LA VRAIE VALEUR D'UNE PROMOTION",
                    items = listOf(
                        "1. Est-ce un vrai prix de départ ? Compare avec une autre boutique.",
                        "2. En avais-je besoin avant de voir la promo ? Si non, tu ne gagnes pas 7 000 Ar : tu en dépenses 18 000 Ar.",
                        "3. Le prix promo tient-il dans mon budget ?"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.BON_REFLEXE,
                title = "LE RÉFLEXE 3 PRIX",
                content = "Avant tout achat de plus de 10 000 Ar, relève trois prix : boutique, marché, en ligne. Dix minutes de comparaison font souvent économiser 10 à 20 %."
            )
        ),
        Planche(
            id = "c1_p13",
            cahierId = 1,
            pageNumber = 13,
            sectionNumber = "13 NOTION · AGIR",
            title = "Gagner de l'argent jeune",
            accroche = "On peut commencer tôt, à petite échelle, sans jamais mettre l'école en danger. L'idée n'est pas de devenir riche : c'est d'apprendre ce que vaut le travail, et de découvrir ce qu'on sait faire.",
            keyCards = listOf(
                KeyCard("G.1", "RENDRE SERVICE", "Cours particuliers, garde d'enfants, courses pour les voisins, aide au jardin."),
                KeyCard("G.2", "CRÉER", "Bracelets, cartes, jus, gâteaux — vendus autour de soi ou lors des fêtes."),
                KeyCard("G.3", "REVENDRE", "Livres scolaires, jouets, vêtements en bon état dont on ne se sert plus."),
                KeyCard("G.4", "NUMÉRIQUE", "Retouche photo, montage vidéo, aide informatique : compétences très demandées.")
            ),
            detailedBoxes = listOf(
                DetailedBox(
                    title = "MINI-PLAN : VENDRE 20 VERRES DE JUS UN SAMEDI",
                    items = listOf(
                        "Fruits, sucre, glace : − 6 000 Ar",
                        "Gobelets : − 2 000 Ar",
                        "20 verres × 700 Ar : + 14 000 Ar",
                        "Bénéfice net = 6 000 Ar (soit 300 Ar de marge par verre, pour 4 h de travail)"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.REGLE_DOR,
                title = "PRIORITÉ ABSOLUE",
                content = "L'école d'abord, toujours. Un petit revenu aujourd'hui ne doit jamais coûter un diplôme demain — c'est le pire investissement possible."
            )
        ),
        Planche(
            id = "c1_p14",
            cahierId = 1,
            pageNumber = 14,
            sectionNumber = "14 NOTION · AGIR",
            title = "Donner et partager",
            accroche = "Bien gérer son argent, ce n'est pas tout garder pour soi. Aider sa famille, contribuer à un projet commun, offrir : cela fait partie de la vie — et cela se prévoit dans le budget, comme le reste.",
            keyCards = listOf(
                KeyCard("D.1", "DONNER DU TEMPS", "Aider un plus jeune à réviser, participer à une action de quartier. Gratuit et très précieux."),
                KeyCard("D.2", "DONNER UN PEU", "Une petite part fixe du budget — 5 % par exemple — pour un cadeau, une collecte."),
                KeyCard("D.3", "DONNER AVEC RÈGLES", "Un don prévu ne met pas le budget en danger. Un don sous pression, si. Apprends à dire « pas cette fois ».")
            ),
            detailedBoxes = listOf(
                DetailedBox(
                    title = "LE BUDGET AJUSTÉ SUR 20 000 Ar",
                    items = listOf(
                        "Besoins (50%) : 10 000 Ar",
                        "Envies (25%) : 5 000 Ar",
                        "Épargne (20%) : 4 000 Ar",
                        "Partage (5%) : 1 000 Ar"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "ÉQUILIBRE",
                content = "Donner sans compter épuise ; ne jamais donner isole. Une part petite mais régulière permet d'être généreux longtemps — et de le rester même quand le budget est serré."
            )
        ),
        Planche(
            id = "c1_p15",
            cahierId = 1,
            pageNumber = 15,
            sectionNumber = "15 NOTION · SE PROTÉGER",
            title = "L'inflation",
            accroche = "L'inflation, c'est quand les prix montent en général. Avec le même billet, on achète moins qu'avant. Un billet caché sous le matelas perd donc de la valeur avec le temps — sans que personne ne le vole.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "EXEMPLE — 7 % D'INFLATION PAR AN SUR LE RIZ",
                    items = listOf(
                        "Aujourd'hui : 1 kg de riz = 3 000 Ar",
                        "Dans 1 an : 1 kg de riz = 3 210 Ar",
                        "Dans 5 ans : 1 kg de riz = env. 4 200 Ar",
                        "Dans 10 ans : 1 kg de riz = env. 5 900 Ar",
                        "Tes 10 000 Ar d'aujourd'hui n'achèteront dans 10 ans que la moitié de biens sans placement."
                    )
                ),
                DetailedBox(
                    title = "COMMENT SE PROTÉGER ?",
                    items = listOf(
                        "Placer plutôt que cacher : un placement dont le taux dépasse l'inflation.",
                        "Acheter utile au bon moment : fournitures avant la rentrée.",
                        "Développer ses compétences : c'est le seul placement que l'inflation ne peut pas grignoter."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "À RETENIR",
                content = "L'inflation est invisible mais réelle. Ne pas placer son épargne, c'est accepter de perdre un peu chaque année. Épargner, oui — mais faire travailler cette épargne aussi."
            )
        ),

        // ================= CAHIER 02: VIE & PSYCHOLOGIE =================
        Planche(
            id = "c2_p01",
            cahierId = 2,
            pageNumber = 1,
            sectionNumber = "01 NOTION · SE COMPRENDRE",
            title = "Comprendre ses émotions",
            accroche = "Une émotion n'est ni bonne ni mauvaise : c'est un signal. La peur protège, la colère dit qu'une limite est franchie, la tristesse dit qu'on a perdu quelque chose qui comptait. Les nommer, c'est déjà les apaiser.",
            keyCards = listOf(
                KeyCard("E.1", "1 · NOMMER", "« Je ressens de la colère. » Mettre un mot précis dessus baisse déjà son intensité."),
                KeyCard("E.2", "2 · ACCUEILLIR", "L'émotion a le droit d'exister. La combattre la fait grossir ; l'observer la fait passer."),
                KeyCard("E.3", "3 · CHOISIR", "L'émotion informe, elle ne commande pas. Entre le ressenti et l'action, il y a toujours un espace.")
            ),
            detailedBoxes = listOf(
                DetailedBox(
                    title = "LES 6 ÉMOTIONS DE BASE",
                    items = listOf(
                        "Joie : énergie, envie de partager",
                        "Peur : signal d'un danger réel ou imaginé",
                        "Colère : signal qu'une limite a été franchie",
                        "Tristesse : une perte, un manque",
                        "Dégoût : quelque chose à rejeter pour se préserver",
                        "Surprise : l'imprévu à évaluer"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.EXERCICE_PRATIQUE,
                title = "À ESSAYER CE SOIR",
                content = "Note trois émotions ressenties dans la journée, avec la situation qui les a déclenchées. Après une semaine, tu verras des motifs — et c'est le début du contrôle."
            )
        ),
        Planche(
            id = "c2_p02",
            cahierId = 2,
            pageNumber = 2,
            sectionNumber = "02 NOTION · SE COMPRENDRE",
            title = "Les émotions dans le corps",
            accroche = "Avant d'arriver à la tête, l'émotion passe par le corps : gorge serrée, mains moites, ventre noué. Apprendre à lire ces signaux, c'est repérer l'émotion tôt — quand elle est encore facile à calmer.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "SIGNAL → ÉMOTION PROBABLE → GESTE QUI AIDE",
                    items = listOf(
                        "Cœur qui s'emballe avant un oral (Stress, peur) → Expirer lentement, 6 fois.",
                        "Mâchoire serrée, chaleur au visage (Colère) → S'éloigner 5 minutes avant de répondre.",
                        "Fatigue lourde, envie de rien (Tristesse) → Sortir marcher, appeler quelqu'un.",
                        "Ventre noué en voyant un message (Anxiété) → Nommer ce qu'on craint, précisément."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.CONSEIL_SEMAINE,
                title = "LE SCAN DE 30 SECONDES",
                content = "Ferme les yeux, parcours ton corps de la tête aux pieds et demande-toi où ça serre. Tu n'as rien à changer : observer suffit souvent à faire baisser la tension d'un cran."
            )
        ),
        Planche(
            id = "c2_p03",
            cahierId = 2,
            pageNumber = 3,
            sectionNumber = "03 NOTION · SE COMPRENDRE",
            title = "Les pensées automatiques",
            accroche = "Ce n'est pas la situation qui déclenche l'émotion, c'est ce qu'on se raconte à son sujet. Ces pensées arrivent seules, très vite, et sont souvent déformées. Les repérer, c'est reprendre la main.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "LES 6 DISTORSIONS COURANTES",
                    items = listOf(
                        "Tout ou rien : « J'ai eu 9/20, je suis nul. » Une note n'est pas une identité.",
                        "Généralisation : « Ça rate toujours. » Toujours ? Cherche un contre-exemple.",
                        "Lecture de pensée : « Ils pensent que je suis ridicule. » Tu ne peux pas savoir.",
                        "Catastrophe : « Si je rate, ma vie est finie. » Quel est le scénario réaliste ?",
                        "Filtre négatif : Dix compliments, une critique : tu ne retiens que la critique.",
                        "« Je dois » : « Je dois être parfait. » Qui a écrit cette règle ?"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "À RETENIR",
                content = "Une pensée n'est pas un fait. Quand une phrase intérieure te fait mal, écris-la, puis écris la version qu'un ami bienveillant et honnête proposerait. Compare."
            )
        ),
        Planche(
            id = "c2_p04",
            cahierId = 2,
            pageNumber = 4,
            sectionNumber = "04 NOTION · SE COMPRENDRE",
            title = "Gérer le stress et la pression",
            accroche = "Le stress n'est pas l'ennemi : à petite dose, il concentre et donne de l'énergie. Trop fort ou trop long, il paralyse. Le but n'est pas de ne plus stresser, c'est de rester dans la zone utile.",
            keyCards = listOf(
                KeyCard("S.1", "RESPIRER", "4 s inspire, 6 s expire, 6 fois. L'expiration longue calme le cœur."),
                KeyCard("S.2", "DÉCOUPER", "Une montagne devient dix collines. Écris la première petite étape et fais-la."),
                KeyCard("S.3", "BOUGER", "20 minutes de marche ou de sport évacuent l'énergie du stress."),
                KeyCard("S.4", "EN PARLER", "Dire « je suis sous pression » à quelqu'un divise déjà la charge.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.CONSEIL_SEMAINE,
                title = "AVANT UN EXAMEN — LE PLAN EN 3 TEMPS",
                content = "La veille : réviser léger, dormir. Le matin : manger, arriver tôt, respirer 2 minutes. Pendant : lire tout le sujet, commencer par ce que tu sais. La pression baisse dès que tu agis."
            )
        ),
        Planche(
            id = "c2_p05",
            cahierId = 2,
            pageNumber = 5,
            sectionNumber = "05 NOTION · TENIR BON",
            title = "Apprendre à dire non",
            accroche = "Dire non n'est pas être méchant : c'est être clair. Chaque « oui » forcé est un « non » à soi-même. On peut refuser une demande tout en respectant la personne — et c'est même comme ça qu'on gagne son respect.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "EXEMPLES DE REFUS NETS & COURTOIS",
                    items = listOf(
                        "« Prête-moi tes devoirs à copier » → « Non, je ne prête pas mes devoirs. Si tu veux, on peut faire l'exercice ensemble à la récré. »",
                        "« Viens, tout le monde y va » → « Non, pas ce soir. » (Puis silence, pas d'excuse inventée).",
                        "« Envoie-moi une photo de toi » → « Non. » Point. Bloque et parle-en à un adulte.",
                        "« Un vrai ami ne refuserait pas » → « Un vrai ami accepte un non. »"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.EXERCICE_PRATIQUE,
                title = "ENTRAÎNEMENT",
                content = "Devant un miroir ou avec un ami : dis « non » calmement, en regardant la personne, sans t'excuser. Cinq fois. Ça paraît idiot — et ça marche : le corps apprend le calme."
            )
        ),
        Planche(
            id = "c2_p06",
            cahierId = 2,
            pageNumber = 6,
            sectionNumber = "06 NOTION · TENIR BON",
            title = "Accepter l'échec sans abandonner",
            accroche = "Personne ne réussit du premier coup ce qui compte. L'échec n'est pas le contraire de la réussite : c'est une étape du chemin. Ce qui fait la différence, ce n'est pas de tomber, c'est ce qu'on fait dans l'heure qui suit.",
            keyCards = listOf(
                KeyCard("R.1", "SÉPARER", "Ce que j'ai fait ≠ ce que je suis. Un échec est un événement, pas une étiquette."),
                KeyCard("R.2", "ANALYSER", "Qu'est-ce qui dépendait de moi ? C'est là, et seulement là, qu'il faut agir."),
                KeyCard("R.3", "RÉESSAYER VITE", "Plus on attend, plus la peur grossit. Refaire dans les 48 h."),
                KeyCard("R.4", "RACONTER", "Dire « j'ai raté et j'ai appris ça » à quelqu'un enlève la honte.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "LE MOT MAGIQUE : « PAS ENCORE »",
                content = "« Je n'y arrive pas » ferme la porte. « Je n'y arrive pas encore » la laisse ouverte. Le cerveau apprend par la répétition, pas par la magie — chaque essai compte, même raté."
            )
        ),
        Planche(
            id = "c2_p07",
            cahierId = 2,
            pageNumber = 7,
            sectionNumber = "07 NOTION · TENIR BON",
            title = "Développer sa confiance en soi",
            accroche = "La confiance ne se décrète pas et ne s'attend pas : elle se construit par l'action. On ne devient pas confiant puis capable — on devient capable, petit pas après petit pas, et la confiance suit.",
            schema = SchemaData(
                type = SchemaType.STAIRS_PROGRESSION,
                title = "L'escalier du courage : Chaque marche est une petite peur traversée",
                items = listOf(
                    SchemaItem("1", "Oser poser une question", "En classe ou en groupe"),
                    SchemaItem("2", "Parler à un nouveau", "Créer un premier contact"),
                    SchemaItem("3", "Présenter un exposé", "Prendre la parole en public"),
                    SchemaItem("4", "Défendre mon avis", "S'exprimer avec calme"),
                    SchemaItem("5", "Tenir un projet", "Mener à terme une action")
                )
            ),
            detailedBoxes = listOf(
                DetailedBox(
                    title = "CE QUI CONSTRUIT vs CE QUI ABÎME",
                    items = listOf(
                        "✅ Construit : Tenir une petite promesse faite à soi-même chaque jour.",
                        "✅ Construit : Noter le soir une chose réussie, même minuscule.",
                        "✅ Construit : Se comparer à soi d'hier, pas aux autres.",
                        "❌ Abîme : Se parler comme on ne parlerait jamais à un ami.",
                        "❌ Abîme : Attendre d'être 'prêt' pour commencer."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.EXERCICE_PRATIQUE,
                title = "L'EXERCICE DES 3 RÉUSSITES",
                content = "Chaque soir, trois lignes : une chose que j'ai osée, une chose que j'ai finie, une chose que j'ai apprise. En un mois, tu auras 90 preuves écrites que tu es capable. Le cerveau croit ce qu'il relit."
            )
        ),
        Planche(
            id = "c2_p08",
            cahierId = 2,
            pageNumber = 8,
            sectionNumber = "08 NOTION · TENIR BON",
            title = "Réseaux sociaux et comparaison",
            accroche = "Sur les réseaux, on compare ses coulisses à la scène des autres. Personne ne publie ses échecs, ses doutes, ses journées vides. Se comparer à un montage, c'est perdre à coup sûr.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "TROIS RÉGLAGES QUI CHANGENT TOUT",
                    items = listOf(
                        "Le tri : Désabonne-toi de tout compte qui te fait te sentir moins bien après. C'est un droit, pas une impolitesse.",
                        "Le temps : Fixe une limite (par exemple 45 min/jour) et vérifie-la.",
                        "Le sens : Avant de publier : pour qui ? pourquoi ? Si c'est pour prouver quelque chose, attends une heure."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "À RETENIR",
                content = "Ton fil d'actualité est une vitrine, pas la vie. La seule comparaison utile : toi aujourd'hui, face à toi il y a six mois."
            )
        ),
        Planche(
            id = "c2_p09",
            cahierId = 2,
            pageNumber = 9,
            sectionNumber = "09 NOTION · SE PROTÉGER",
            title = "Reconnaître la manipulation",
            accroche = "Manipuler, c'est obtenir quelque chose de quelqu'un en contournant sa liberté de choisir : par la peur, la culpabilité, la flatterie ou le mensonge. Reconnaître les techniques, c'est déjà s'en protéger.",
            keyCards = listOf(
                KeyCard("M.1", "LA CULPABILITÉ", "« Après tout ce que j'ai fait pour toi… » On te fait payer une dette non contractée."),
                KeyCard("M.2", "L'URGENCE", "« Décide maintenant, sinon c'est trop tard. » La pression empêche de réfléchir."),
                KeyCard("M.3", "LA FLATTERIE", "« Toi tu es différent, tu comprends, toi. » Un compliment qui précède une demande."),
                KeyCard("M.4", "L'ISOLEMENT", "« Tes amis ne te comprennent pas, moi si. » On t'éloigne de tes proches."),
                KeyCard("M.5", "LE SECRET", "« Ça reste entre nous. » Une relation saine n'a pas besoin de cachotteries."),
                KeyCard("M.6", "LE CHAUD-FROID", "Gentil un jour, glacial le lendemain : on te fait courir après son approbation.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.BON_REFLEXE,
                title = "LE RÉFLEXE QUI DÉSAMORCE",
                content = "« Je vais y réfléchir, je te dis demain. » Le manipulateur déteste le délai : c'est là que la pression se voit. Et parles-en à quelqu'un d'extérieur — la manipulation ne résiste pas à un regard neuf."
            )
        ),
        Planche(
            id = "c2_p10",
            cahierId = 2,
            pageNumber = 10,
            sectionNumber = "10 NOTION · SE PROTÉGER",
            title = "Gérer les conflits",
            accroche = "Un conflit n'est pas un échec de la relation : c'est le signe que deux besoins se cognent. Mal géré, il laisse des rancunes ; bien géré, il rapproche. La méthode tient en quatre lettres : DESC.",
            keyCards = listOf(
                KeyCard("D", "DÉCRIRE", "Les faits, sans juger. « Hier tu as raconté à la classe ce que je t'avais dit en privé. »"),
                KeyCard("E", "EXPRIMER", "Ton ressenti, en « je ». « Je me suis senti trahi et gêné. »"),
                KeyCard("S", "SOLUTION", "Ce que tu proposes. « J'aimerais que ce que je te confie reste entre nous. »"),
                KeyCard("C", "CONSÉQUENCE", "Le positif si ça change. « Comme ça je pourrai continuer à te faire confiance. »")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.SIGNAL_ALERTE,
                title = "QUAND ÇA DÉGÉNÈRE",
                content = "Si la voix monte, si les insultes arrivent, si tu as peur : arrête et pars. « On en reparle quand on sera calmes. » Ce n'est pas fuir, c'est protéger la relation — et toi."
            )
        ),
        Planche(
            id = "c2_p11",
            cahierId = 2,
            pageNumber = 11,
            sectionNumber = "11 NOTION · SE PROTÉGER",
            title = "Choisir son entourage",
            accroche = "On devient un peu comme les cinq personnes qu'on fréquente le plus. Ce n'est pas une raison pour juger les gens, mais c'est une raison pour choisir avec soin à qui on donne son temps et sa confiance.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "UN AMI QUI FAIT DU BIEN vs UNE RELATION QUI USE",
                    items = listOf(
                        "✅ Ami bienfaisant : Tu te sens plus léger après l'avoir vu. Il se réjouit de tes réussites sans jalousie. Il respecte tes « non ».",
                        "❌ Relation usante : Tu marches sur des œufs, peur de décevoir. Il se moque de toi « pour rire ». Il n'est là que par besoin."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.CONSEIL_SEMAINE,
                title = "SANS DRAME",
                content = "S'éloigner d'une relation qui use ne demande ni scène ni explication : on répond moins vite, on propose moins, on investit ailleurs. Et on garde son énergie pour ceux qui la méritent."
            )
        ),
        Planche(
            id = "c2_p12",
            cahierId = 2,
            pageNumber = 12,
            sectionNumber = "12 NOTION · SE PROTÉGER",
            title = "Savoir demander de l'aide",
            accroche = "Demander de l'aide n'est pas une faiblesse : c'est une compétence. Les gens les plus solides sont ceux qui savent dire « je n'y arrive pas seul » avant que la situation ne devienne trop lourde.",
            keyCards = listOf(
                KeyCard("A.1", "UN AMI", "Pour se sentir moins seul, être écouté, rire un peu."),
                KeyCard("A.2", "UN PARENT / PROCHE", "Pour ce qui pèse à la maison, à l'école, dans ta tête."),
                KeyCard("A.3", "UN ADULTE DU LYCÉE", "Pour un harcèlement, une pression, une difficulté scolaire."),
                KeyCard("A.4", "UN PRO DE SANTÉ", "Quand ça dure, quand ça empêche de vivre : médecin, psychologue.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "À RETENIR",
                content = "Aider quelqu'un fait du bien à celui qui aide. En demandant, tu ne prends rien : tu offres à l'autre une occasion d'être utile. La plupart des gens attendent seulement qu'on leur demande."
            )
        ),
        Planche(
            id = "c2_p13",
            cahierId = 2,
            pageNumber = 13,
            sectionNumber = "13 NOTION · AVANCER",
            title = "Motivation ≠ discipline",
            accroche = "La motivation est une émotion : elle monte, elle descend, elle disparaît un lundi pluvieux. La discipline est une habitude : elle est là même quand l'envie n'y est pas. Ceux qui avancent ne sont pas plus motivés — ils ont juste arrêté d'attendre de l'être.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "CONSTRUIRE UNE HABITUDE EN 4 RÉGLAGES",
                    items = listOf(
                        "Minuscule : 10 minutes par jour, pas 2 heures.",
                        "Fixe : Même heure, même lieu.",
                        "Visible : Cahier ou livre ouvert sur le bureau.",
                        "Coché : Une croix par jour sur ton tracker."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.REGLE_DOR,
                title = "LA RÈGLE DES 2 JOURS",
                content = "Rater un jour, ça arrive à tout le monde. Ne jamais rater deux jours de suite : c'est la seule règle. Le premier jour raté est un accident ; le deuxième est le début d'une nouvelle habitude — celle d'arrêter."
            )
        ),
        Planche(
            id = "c2_p14",
            cahierId = 2,
            pageNumber = 14,
            sectionNumber = "14 NOTION · AVANCER",
            title = "Sommeil, écrans et humeur",
            accroche = "L'humeur ne dépend pas que des idées : elle dépend du corps. Un cerveau qui manque de sommeil voit tout en noir, se vexe plus vite et apprend moins. À ton âge, il a besoin de 8 à 10 heures — et l'écran est son pire voleur.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "UNE ROUTINE DU SOIR EN 4 GESTES",
                    items = listOf(
                        "21h00 : Dernier message envoyé, téléphone en mode avion posé loin du lit.",
                        "21h15 : Préparer le sac et les vêtements : le matin sera plus calme.",
                        "21h30 : Lire, écrire trois lignes dans son journal, respirer.",
                        "22h00 : Lumière éteinte. Même heure chaque soir : le corps aime la régularité."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "À RETENIR",
                content = "Avant de te demander « qu'est-ce qui ne va pas chez moi ? », demande-toi : ai-je dormi, mangé, bougé, vu la lumière du jour ? Souvent, la réponse est là — et elle est réparable en 48 heures."
            )
        ),

        // ================= CAHIER 03: TRAVAIL & CARRIÈRE =================
        Planche(
            id = "c3_p01",
            cahierId = 3,
            pageNumber = 1,
            sectionNumber = "01 NOTION · SE FAIRE REMARQUER",
            title = "Comment on trouve vraiment un emploi",
            accroche = "La plupart des postes ne sont jamais publiés : ils se pourvoient par recommandation, par candidature spontanée ou parce que quelqu'un a pensé à toi au bon moment. Attendre l'annonce, c'est se battre sur la plus petite part du gâteau.",
            keyCards = listOf(
                KeyCard("1", "LE RÉSEAU", "Famille, voisins, anciens élèves, profs : dire à tous ce que tu cherches précisément."),
                KeyCard("2", "LE SPONTANÉ", "Écrire aux entreprises qui te plaisent, même sans annonce. 1 sur 10 répond : c'est énorme."),
                KeyCard("3", "LES ANNONCES", "Sites, affiches, groupes. Utile — mais tu es en concurrence avec tout le monde."),
                KeyCard("4", "LA PREUVE", "Faire d'abord un petit travail visible (stage, projet, bénévolat). On embauche ce qu'on a vu.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.CONSEIL_SEMAINE,
                title = "LA PHRASE QUI OUVRE DES PORTES",
                content = "« Je cherche un stage / un premier poste en ___ à partir de ___. Si tu entends parler de quelque chose, tu penses à moi ? » Dite à 30 personnes, cette phrase vaut plus que 100 CV envoyés au hasard."
            )
        ),
        Planche(
            id = "c3_p02",
            cahierId = 3,
            pageNumber = 2,
            sectionNumber = "02 NOTION · SE FAIRE REMARQUER",
            title = "Connaître ses compétences",
            accroche = "Avant de convaincre les autres, il faut savoir ce qu'on apporte. Une compétence, ce n'est pas un diplôme : c'est quelque chose que tu sais faire, que tu peux prouver, et qui rend service à quelqu'un.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "LES TROIS PILIERS",
                    items = listOf(
                        "Savoirs : Ce que je connais (langues, comptabilité, secteur agricole/textile...)",
                        "Savoir-faire : Ce que je sais faire (réparer un téléphone, tableau Excel propre, retoucher des photos...)",
                        "Savoir-être : Comment je travaille (ponctuel, calme sous pression, contact facile, persévérant...)"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "UNE COMPÉTENCE SANS PREUVE EST UNE OPINION",
                content = "« Je suis organisé » ne vaut rien. « J'ai géré le planning de 12 bénévoles pendant la fête de l'école sans aucun créneau vide » — ça, c'est une compétence prouvée avec un résultat chiffré."
            )
        ),
        Planche(
            id = "c3_p03",
            cahierId = 3,
            pageNumber = 3,
            sectionNumber = "03 NOTION · SE FAIRE REMARQUER",
            title = "Faire un bon CV",
            accroche = "Un recruteur passe moins de 30 secondes sur un CV. Il ne lit pas : il cherche. Ton CV doit donc répondre en un coup d'œil à trois questions — qui es-tu, que sais-tu faire, qu'as-tu déjà fait.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "LES 6 ERREURS QUI ÉLIMINENT EN 5 SECONDES",
                    items = listOf(
                        "Fautes d'orthographe (fais relire par deux personnes).",
                        "Adresse e-mail fantaisiste : crée-en une sobre (prenom.nom).",
                        "Le même CV envoyé partout sans adaptation.",
                        "Mensonge vérifiable (niveau de langue, diplôme).",
                        "Mise en page chargée, trois polices, couleurs criardes.",
                        "Fichier mal nommé : 'CV.pdf' au lieu de 'CV_Nom_Prenom.pdf'."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.EXERCICE_PRATIQUE,
                title = "LE TEST DES 30 SECONDES",
                content = "Donne ton CV à quelqu'un, retire-le au bout de 30 secondes et demande : « qu'as-tu retenu ? » S'il ne peut pas dire ton titre visé et une compétence, recommence la mise en page."
            )
        ),
        Planche(
            id = "c3_p04",
            cahierId = 3,
            pageNumber = 4,
            sectionNumber = "04 NOTION · SE FAIRE REMARQUER",
            title = "Le portfolio : montrer plutôt que dire",
            accroche = "Le CV dit ce que tu sais faire ; le portfolio le prouve. Photos, textes, tableaux, code, vidéos, objets réparés : tout travail visible peut y entrer. Cinq bons exemples valent mieux que trente moyens.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "CHAQUE PROJET RACONTE LA MÊME HISTOIRE EN 4 LIGNES",
                    items = listOf(
                        "Le contexte : Pour qui, quel besoin ? (« L'épicerie de ma tante n'avait aucune présence en ligne »)",
                        "Mon rôle : Ce que TOI tu as fait (« J'ai pris les photos, rédigé et monté le site »)",
                        "Le résultat : Chiffré (« 40 visites la 1ère semaine, 3 commandes »)",
                        "Ce que j'ai appris : (« Prévoir plus de temps pour les retours client »)"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "PAS ENCORE DE PROJET ?",
                content = "Fabrique-le. Refais l'affiche d'un commerce du quartier, propose gratuitement une aide à une association, documente une réparation. Un projet fictif bien fait montre autant qu'un projet payé."
            )
        ),
        Planche(
            id = "c3_p05",
            cahierId = 3,
            pageNumber = 5,
            sectionNumber = "05 NOTION · CONVAINCRE",
            title = "Passer un entretien",
            accroche = "Un entretien n'est pas un examen : c'est une conversation où deux personnes vérifient qu'elles peuvent travailler ensemble. On le réussit avant d'entrer dans la pièce — par la préparation.",
            keyCards = listOf(
                KeyCard("S", "SITUATION", "« À la buvette de la fête du lycée, on manquait de monnaie et la file s'allongeait. »"),
                KeyCard("T", "TÂCHE", "« J'étais responsable de la caisse. »"),
                KeyCard("A", "ACTION", "« J'ai proposé un tarif rond, envoyé chercher de la monnaie et servi en priorité les petites sommes. »"),
                KeyCard("R", "RÉSULTAT", "« En 10 minutes plus de file, et 60 000 Ar de plus encaissés. »")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.CONSEIL_SEMAINE,
                title = "LE TRAC",
                content = "Tout le monde l'a. Il baisse avec l'entraînement : fais-toi interroger par un ami à voix haute, trois fois. À la troisième, les réponses sortent seules et tu parais calme."
            )
        ),
        Planche(
            id = "c3_p06",
            cahierId = 3,
            pageNumber = 6,
            sectionNumber = "06 NOTION · VALORISER",
            title = "Fixer le prix de son travail",
            accroche = "Trop bas, tu t'épuises et tu attires les mauvais clients ; trop haut sans preuve, personne ne signe. Un prix juste se calcule à partir de trois choses : ce que ça te coûte, ce que fait le marché, et ce que ça rapporte au client.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "CALCUL DU TAUX HORAIRE MINIMUM",
                    items = listOf(
                        "Ce que je veux gagner par mois : 120 000 Ar",
                        "+ Frais mensuels (données, transport, outils) : 30 000 Ar",
                        "= À couvrir chaque mois : 150 000 Ar",
                        "Heures vendables par mois (10h/semaine, 1/3 non facturable) : env. 28 h",
                        "Taux horaire minimum : env. 5 400 Ar / h",
                        "Une affiche prenant 4h se facture au moins 22 000 Ar au forfait."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "LE PRIX « AMI »",
                content = "Travailler gratuitement pour la famille proche, oui, si tu le décides. Mais dès que c'est un commerce, un prix — même modeste — est indispensable : ce qui ne coûte rien n'est ni respecté ni prioritaire."
            )
        ),

        // ================= CAHIER 04: ENTREPRENEURIAT =================
        Planche(
            id = "c4_p01",
            cahierId = 4,
            pageNumber = 1,
            sectionNumber = "01 NOTION · TROUVER",
            title = "Un problème qui mérite d'être résolu",
            accroche = "La plupart des projets échouent parce qu'ils répondent à un problème que personne n'a vraiment. Une bonne idée n'est pas « originale » : elle est ennuyeuse, précise, et quelqu'un perd du temps ou de l'argent à cause d'elle chaque semaine.",
            keyCards = listOf(
                KeyCard("F.1", "FRÉQUENT", "Le problème revient souvent : chaque jour ou chaque semaine."),
                KeyCard("F.2", "DOULOUREUX", "Il coûte du temps, de l'argent ou de la gêne. Si c'est juste un peu agaçant, personne ne paiera."),
                KeyCard("F.3", "RECONNU", "Les gens savent qu'ils l'ont et en parlent déjà."),
                KeyCard("F.4", "ATTEIGNABLE", "Tu peux toucher ces personnes directement dans ton quartier, ton lycée ou ton marché.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.EXERCICE_PRATIQUE,
                title = "L'EXERCICE DE LA SEMAINE",
                content = "Note pendant 7 jours tous les moments où quelqu'un râle, perd du temps ou paie trop cher autour de toi. Tu auras 20 lignes. Trois d'entre elles reviendront plusieurs fois : ce sont tes vraies opportunités."
            )
        ),
        Planche(
            id = "c4_p02",
            cahierId = 4,
            pageNumber = 2,
            sectionNumber = "02 NOTION · TESTER",
            title = "Le produit minimum (MVP)",
            accroche = "Le produit minimum, c'est la plus petite version qui résout déjà le problème pour un vrai client. Pas une version bâclée : une version réduite mais qui fonctionne — et qu'on peut livrer cette semaine.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "PAR MORCEAUX vs PAR VERSIONS",
                    items = listOf(
                        "❌ Par morceaux : Une roue, puis un châssis, puis un moteur → Le client ne roule qu'à la toute fin.",
                        "✅ Par versions : Une planche à roulettes, puis un vélo, puis une moto → À chaque étape le client se déplace déjà !"
                    )
                ),
                DetailedBox(
                    title = "ON GARDE vs ON COUPE",
                    items = listOf(
                        "On garde : Ce qui résout le problème principal et ce qui apporte de la valeur immédiate.",
                        "On coupe : Le logo parfait, le site vitrine complexe, l'automatisation lourde (au début, on fait à la main)."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.CONSEIL_SEMAINE,
                title = "LA QUESTION DE LA SEMAINE",
                content = "« Qu'est-ce que je pourrais livrer à un vrai client d'ici sept jours ? » La réponse est presque toujours possible — et elle t'apprendra plus que six mois de préparation théorique."
            )
        ),
        Planche(
            id = "c4_p03",
            cahierId = 4,
            pageNumber = 3,
            sectionNumber = "03 NOTION · TENIR",
            title = "Chiffre d'affaires n'est pas bénéfice",
            accroche = "« J'ai fait 500 000 Ar ce mois-ci ! » — d'accord, mais combien reste-t-il ? Le chiffre d'affaires, c'est tout ce qui entre. Le bénéfice, c'est ce qui reste une fois tout payé. C'est la confusion la plus coûteuse qui existe.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "DÉCOMPOSITION RÉELLE D'UN MOIS",
                    items = listOf(
                        "Chiffre d'affaires encaissé : 500 000 Ar",
                        "− Achats de matières : 210 000 Ar",
                        "− Frais fixes (transport, outils) : 90 000 Ar",
                        "− Ton salaire mérité : 120 000 Ar",
                        "= BÉNÉFICE NET RÉEL : 80 000 Ar (soit 16 % du CA)"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.REGLE_DOR,
                title = "LA RÈGLE DES TROIS POCHES",
                content = "Sépare l'argent dès qu'il rentre : une part pour les dépenses de l'activité, une part pour ton salaire, une part de réserve d'urgence. Trois enveloppes ou trois comptes — mais jamais un seul tas."
            )
        ),

        // ================= CAHIER 05: COMMUNICATION =================
        Planche(
            id = "c5_p01",
            cahierId = 5,
            pageNumber = 1,
            sectionNumber = "01 NOTION · ÉCOUTER",
            title = "Ce qui se joue quand on parle",
            accroche = "Entre ce que tu penses et ce que l'autre retient, il y a six étapes — et à chacune, une partie se perd. Comprendre où ça fuit, c'est déjà mieux communiquer.",
            keyCards = listOf(
                KeyCard("C.1", "LES MOTS", "Ce que tu dis. Important — mais c'est la plus petite part du message perçu."),
                KeyCard("C.2", "LA VOIX", "Le ton, le rythme, les silences : ils donnent l'émotion et la crédibilité."),
                KeyCard("C.3", "LE CORPS", "Le regard, la posture, les mains, le visage : c'est ce qu'on croit en premier.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.REGLE_DOR,
                title = "LA RÈGLE DE LA VÉRIFICATION",
                content = "Ne demande jamais « c'est clair ? » — tout le monde répond oui par réflexe. Demande : « qu'est-ce que tu vas en faire ? » ou « tu le redirais comment ? ». La réponse te dit ce qui est vraiment passé."
            )
        ),
        Planche(
            id = "c5_p02",
            cahierId = 5,
            pageNumber = 2,
            sectionNumber = "02 NOTION · PARLER",
            title = "Présenter une idée clairement",
            accroche = "Une idée claire tient en une phrase. Si tu as besoin de trois minutes pour la poser, elle n'est pas encore claire — pour toi non plus. La structure fait la moitié du travail.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "LA STRUCTURE EN 4 TEMPS",
                    items = listOf(
                        "1. Le problème : « Chaque samedi, 40 élèves attendent 30 minutes pour manger. »",
                        "2. La solution : « Un système de précommande la veille : fini l'attente. »",
                        "3. La preuve : « Testé deux samedis : l'attente est tombée à 6 minutes. »",
                        "4. La demande : « J'ai besoin de votre accord pour l'étendre dès le mois prochain. »"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.CONSEIL_SEMAINE,
                title = "LE TEST DE L'ASCENSEUR",
                content = "Peux-tu présenter ton idée en 30 secondes à quelqu'un qui ne connaît rien au sujet et lui donner envie d'en savoir plus ? Si non, structure-la avant de parler."
            )
        ),

        // ================= CAHIER 06: VIE QUOTIDIENNE =================
        Planche(
            id = "c6_p01",
            cahierId = 6,
            pageNumber = 1,
            sectionNumber = "01 NOTION · LES PAPIERS",
            title = "Comprendre un contrat",
            accroche = "Signer, c'est s'engager. Un contrat n'a pas besoin d'être long pour compter : un bail, un abonnement, un devis accepté. La règle absolue : on ne signe jamais ce qu'on n'a pas lu et compris.",
            keyCards = listOf(
                KeyCard("1", "LIRE TOUT", "Ce qui est écrit petit est écrit exprès. Prends 10 minutes devant la personne."),
                KeyCard("2", "CHIFFRES & DATES", "Montants en chiffres et en lettres, dates précises, pas 'bientôt'."),
                KeyCard("3", "CASES VIDES", "Ne signe jamais un document avec un blanc : il pourrait être rempli après."),
                KeyCard("4", "REPARTIR AVEC UNE COPIE", "Signée par les deux parties. Sans copie, aucune preuve de ce qui a été convenu.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.REGLE_DOR,
                title = "SI TU NE COMPRENDS PAS",
                content = "Dis-le : « Je ne signe pas aujourd'hui, je veux faire relire ce document. » Une personne honnête l'accepte sans problème ; celle qui insiste révèle le piège."
            )
        ),
        Planche(
            id = "c6_p02",
            cahierId = 6,
            pageNumber = 2,
            sectionNumber = "02 NOTION · LE TEMPS",
            title = "Organiser son temps",
            accroche = "On ne gère pas le temps : il passe de toute façon. On gère ses priorités. La différence entre quelqu'un de débordé et quelqu'un d'organisé, ce n'est pas la quantité de travail — c'est l'ordre.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "LA MATRICE DU TEMPS",
                    items = listOf(
                        "Urgent & Important (Faire maintenant) : Examen demain, panne, urgence médicale.",
                        "Important, Pas Urgent (Planifier, C'EST LÀ QUE TOUT SE JOUE) : Réviser, sport, projets d'avenir.",
                        "Urgent, Pas Important (Réduire ou déléguer) : Notifications, sollicitations.",
                        "Ni l'un ni l'autre (Limiter) : Défilement passif sur les réseaux, séries en boucle."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.EXERCICE_PRATIQUE,
                title = "LES DIX MINUTES DU DIMANCHE",
                content = "Une fois par semaine : regarde les 7 jours qui viennent, note les échéances, place tes 3 choses importantes dans des créneaux précis. Ces 10 minutes valent des heures gagnées."
            )
        ),

        // ================= CAHIER 07: À L'ÈRE NUMÉRIQUE =================
        Planche(
            id = "c7_p01",
            cahierId = 7,
            pageNumber = 1,
            sectionNumber = "01 NOTION · SE PROTÉGER",
            title = "Protéger ses comptes",
            accroche = "Ton compte principal — la messagerie — est la clé de tous les autres : c'est par lui qu'on réinitialise les mots de passe. Le protéger en priorité, c'est protéger l'ensemble.",
            keyCards = listOf(
                KeyCard("P.1", "MOT DE PASSE UNIQUE", "Un mot de passe différent pour la messagerie : jamais réutilisé ailleurs."),
                KeyCard("P.2", "DOUBLE VÉRIFICATION", "Un code en plus du mot de passe (2FA). C'est le geste le plus efficace."),
                KeyCard("P.3", "NUMÉRO DE SECOURS", "Un numéro et une adresse de secours vérifiés une fois par an."),
                KeyCard("P.4", "SESSIONS ACTIVES", "Vérifie les appareils connectés et déconnecte ceux inconnus.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.BON_REFLEXE,
                title = "À FAIRE CE SOIR",
                content = "Ouvre les paramètres de sécurité de ta messagerie, active la double vérification et vérifie ton numéro de récupération. Cinq minutes maintenant valent mieux que trois semaines de démarches après un piratage."
            )
        ),
        Planche(
            id = "c7_p02",
            cahierId = 7,
            pageNumber = 2,
            sectionNumber = "02 NOTION · LES MACHINES",
            title = "Comprendre et utiliser l'IA",
            accroche = "Une intelligence artificielle ne « sait » rien et ne « pense » pas : elle prédit la suite de mots la plus probable. Elle est puissante pour reformuler, explorer et structurer, mais peut inventer des faits avec assurance.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "LES 4 PILIERS D'UN BON PROMPT",
                    items = listOf(
                        "1. Le Rôle : « Tu es agronome / professeur de... »",
                        "2. Le Contexte : « Pour des lycéens, dans un cadre scolaire... »",
                        "3. La Tâche : Verbe d'action précis (résume, compare, génère 3 options...)",
                        "4. Le Format : « En 200 mots, avec 3 puces concrètes. »"
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "LA BONNE IMAGE",
                content = "L'IA est comme un assistant très cultivé et très rapide mais qui ne sait pas quand il se trompe. Utile pour dégrossir, jamais pour valider sans relecture. La vérification reste ton travail."
            )
        ),

        // ================= CAHIER 08: LES COMPÉTENCES ESSENTIELLES =================
        Planche(
            id = "c8_p01",
            cahierId = 8,
            pageNumber = 1,
            sectionNumber = "01 NOTION · APPRENDRE",
            title = "Apprendre à apprendre",
            accroche = "Apprendre n'est pas relire jusqu'à ce que ça paraisse familier. Le sentiment de comprendre trompe : la seule preuve d'un savoir acquis, c'est de pouvoir le restituer et l'utiliser sans le support.",
            keyCards = listOf(
                KeyCard("1", "RAPPEL ACTIF", "Fermer le cours, écrire de mémoire tout ce dont on se souvient, puis vérifier."),
                KeyCard("2", "RÉPÉTITION ESPACÉE", "Revoir à J+1, J+7, J+30. Trois rappels courts battent une séance de 5 heures."),
                KeyCard("3", "AUTO-EXPLICATION", "Expliquer la notion à voix haute avec ses propres mots à un ami."),
                KeyCard("4", "ALTERNANCE", "Mélanger les types d'exercices au lieu de répéter mécaniquement le même.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.EXERCICE_PRATIQUE,
                title = "LE TEST DE LA PAGE BLANCHE",
                content = "Ferme tout, prends une feuille blanche et écris ce que tu sais du chapitre. Ce qui manque, c'est exactement ce qu'il te reste à travailler — identifié en 5 minutes !"
            )
        ),
        Planche(
            id = "c8_p02",
            cahierId = 8,
            pageNumber = 2,
            sectionNumber = "02 NOTION · RÉSOUDRE",
            title = "Découper et modéliser",
            accroche = "Un gros problème est une illusion : c'est toujours un empilement de petits problèmes. Savoir découper, c'est transformer l'impossible en une liste de choses faisables.",
            detailedBoxes = listOf(
                DetailedBox(
                    title = "CINQ RÈGLES DE DÉCOUPAGE",
                    items = listOf(
                        "Un morceau = une action avec un verbe précis (appeler, rédiger, mesurer).",
                        "Un responsable par morceau, sinon personne ne le fait.",
                        "Commencer par le plus risqué (ce qui peut bloquer tout le reste).",
                        "Repérer les dépendances (ce qui doit être fini avant la suite).",
                        "Garder une marge de temps (x 1,5 le temps estimé initialement)."
                    )
                )
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.A_RETENIR,
                title = "LA PREMIÈRE PIERRE",
                content = "Quand un projet paraît gigantesque, cherche la plus petite action possible faisable en 5 minutes : un coup de fil, une note, un croquis. Commencer petit n'est pas renoncer à grand : c'est la seule façon d'y arriver."
            )
        ),
        Planche(
            id = "c8_p03",
            cahierId = 8,
            pageNumber = 3,
            sectionNumber = "03 NOTION · SE DIRIGER",
            title = "Se construire sur dix ans",
            accroche = "On surestime ce qu'on peut faire en un an et on sous-estime largement ce qu'on peut faire en dix. La constance ordinaire, tenue longtemps, produit des résultats qui paraissent extraordinaires.",
            keyCards = listOf(
                KeyCard("1", "COMPÉTENCES", "Chaque nouvelle compétence s'appuie sur la précédente et multiplie sa valeur."),
                KeyCard("2", "RÉPUTATION", "Des années à tenir parole, une seconde à la perdre."),
                KeyCard("3", "HABITUDES", "30 minutes de pratique par jour font plus de 1 000 heures en six ans."),
                KeyCard("4", "PREUVES", "Chaque projet fini s'ajoute à un portfolio qui parlera pour toi toute ta vie.")
            ),
            bottomAdvice = BottomAdvice(
                type = AdviceType.CONSEIL_SEMAINE,
                title = "LA QUESTION DES DIX ANS",
                content = "« Qu'est-ce que je peux commencer aujourd'hui qui, fait un peu chaque semaine, me rendra méconnaissable dans dix ans ? » La réponse est presque toujours simple, concrète — et disponible immédiatement."
            )
        )
    )

    // Quizzes for all Cahiers
    val quizzes = listOf(
        // Cahier 1 Quiz
        QuizQuestion("q1_1", 1, 1, "Un téléphone dernier cri, c'est plutôt…", "Un besoin", "Une envie", "B", "Un téléphone permet de communiquer (besoin), mais le modèle dernier cri relève du plaisir et du statut (envie)."),
        QuizQuestion("q1_2", 1, 2, "Dans la règle 50-30-20, les 20 % servent à…", "S'amuser", "Épargner", "B", "20 % de tous les revenus doivent être mis de côté dès réception avant toute dépense."),
        QuizQuestion("q1_3", 1, 3, "Recevoir 15 000 Ar et épargner 20 %, c'est mettre de côté…", "3 000 Ar", "1 500 Ar", "A", "15 000 × 0,20 = 3 000 Ar."),
        QuizQuestion("q1_4", 1, 4, "Les intérêts composés, c'est…", "Des intérêts qui produisent des intérêts", "Un impôt sur l'épargne", "A", "C'est l'effet boule de neige : les gains réinvestis génèrent de nouveaux gains."),
        QuizQuestion("q1_5", 1, 5, "Un message promet 50 000 Ar contre 5 000 Ar envoyés. Tu…", "Envoies vite", "Ignores et préviens un adulte", "B", "Si c'est trop beau pour être vrai, c'est une arnaque. On ne paie jamais pour recevoir de l'argent."),
        QuizQuestion("q1_6", 1, 6, "Règle de 72 : à 6 % par an, l'argent double en…", "6 ans", "12 ans", "B", "72 ÷ 6 = 12 ans."),
        QuizQuestion("q1_7", 1, 7, "1,5 L à 3 600 Ar ou 0,5 L à 1 500 Ar : le moins cher au litre est…", "La grande bouteille", "La petite bouteille", "A", "3 600 / 1.5 = 2 400 Ar/L contre 1 500 / 0.5 = 3 000 Ar/L."),
        QuizQuestion("q1_8", 1, 8, "Emprunter 100 000 Ar à 3 % par mois sur 12 mois coûte environ…", "3 000 Ar d'intérêts", "36 000 Ar d'intérêts", "B", "3 % × 12 mois = 36 % soit 36 000 Ar d'intérêts en sus du capital."),
        QuizQuestion("q1_9", 1, 9, "Quand les prix montent partout, on parle…", "D'inflation", "D'intérêt", "A", "L'inflation érode le pouvoir d'achat de la monnaie qui dort."),
        QuizQuestion("q1_10", 1, 10, "Ton code secret mobile money, tu le donnes…", "À personne", "À un ami de confiance", "A", "Le code secret est strictement personnel et ne doit jamais être partagé."),

        // Cahier 2 Quiz
        QuizQuestion("q2_1", 2, 1, "Une émotion, c'est avant tout…", "Un défaut à corriger", "Un signal à écouter", "B", "L'émotion informe d'un état ou d'une limite franchie."),
        QuizQuestion("q2_2", 2, 2, "Face à une pensée douloureuse, la première question est…", "Quelle preuve ai-je ?", "Comment l'oublier ?", "A", "Une pensée n'est pas un fait : il faut vérifier sa véracité."),
        QuizQuestion("q2_3", 2, 3, "Un peu de stress avant un examen, c'est…", "Dangereux", "Utile, s'il reste modéré", "B", "Le stress modéré mobilise l'énergie et la concentration."),
        QuizQuestion("q2_4", 2, 4, "Pour dire non efficacement, il faut…", "Une phrase claire", "Beaucoup d'explications", "A", "Plus on se justifie, plus on offre de prises à la négociation."),
        QuizQuestion("q2_5", 2, 5, "Après un échec, le meilleur réflexe est de…", "Réessayer vite", "Attendre d'être sûr de réussir", "A", "Réessayer dans les 48h empêche la peur de grandir."),
        QuizQuestion("q2_6", 2, 6, "La confiance en soi se construit surtout…", "En attendant d'être prêt", "En agissant par petits pas", "B", "C'est l'action réussie qui engendre la confiance, pas l'inverse."),
        QuizQuestion("q2_7", 2, 7, "« Ça reste entre nous, ne le dis à personne » est souvent…", "Un signe d'amitié pure", "Un signal de manipulation possible", "B", "Isoler la personne du regard de ses proches est une technique classique de manipulation."),
        QuizQuestion("q2_8", 2, 8, "Dans un conflit, on parle…", "Du problème", "De la personne", "A", "Parler des faits évite les attaques personnelles stériles."),
        QuizQuestion("q2_9", 2, 9, "Demander de l'aide, c'est…", "Une compétence", "Une faiblesse", "A", "Les personnes solides savent solliciter des compétences extérieures au bon moment."),
        QuizQuestion("q2_10", 2, 10, "La motivation et la discipline, c'est…", "La même chose", "Une émotion et une habitude", "B", "La motivation fluctue, la discipline tient les engagements pris."),

        // Cahier 3 Quiz
        QuizQuestion("q3_1", 3, 1, "La plupart des emplois se trouvent…", "Par les annonces", "Par le réseau et le spontané", "B", "70 % des opportunités se trouvent sur le marché caché non publié."),
        QuizQuestion("q3_2", 3, 2, "Un recruteur passe sur un CV environ…", "30 secondes", "10 minutes", "A", "La lecture est sélective et rapide, d'où l'importance de la clarté."),
        QuizQuestion("q3_3", 3, 3, "Le portfolio sert à…", "Prouver ce que le CV affirme", "Remplacer le CV", "A", "Le portfolio apporte la preuve concrète par des réalisations visuelles."),
        QuizQuestion("q3_4", 3, 4, "Un bon message de candidature fait…", "Une page entière", "Trois courts paragraphes", "B", "Court, percutant et personnalisé : Pourquoi eux, Pourquoi toi, Et maintenant."),
        QuizQuestion("q3_5", 3, 5, "La méthode STAR, c'est…", "Situation, Tâche, Action, Résultat", "Sourire, Tenue, Attitude, Regard", "A", "La structure idéale pour raconter une expérience concrète en entretien."),
        QuizQuestion("q3_6", 3, 6, "En entretien, si tu ne sais pas…", "Tu inventes", "Tu dis comment tu apprendrais", "B", "L'honnêteté et la méthode d'apprentissage rassurent bien plus qu'un mensonge."),
        QuizQuestion("q3_7", 3, 7, "Un prix se calcule d'après…", "Le coût, le marché, la valeur", "Ce que demande le client", "A", "Coût (plancher), marché (repère) et valeur créée pour le client (plafond)."),
        QuizQuestion("q3_8", 3, 8, "En négociation, baisser le prix sans rien retirer, c'est…", "Normal", "À éviter", "B", "Toujours échanger une concession de prix contre une réduction de travail."),
        QuizQuestion("q3_9", 3, 9, "Un devis accepté par message écrit…", "Ne vaut rien", "Suffit, garde la capture", "B", "Un accord écrit même court prévient 90 % des litiges."),
        QuizQuestion("q3_10", 3, 10, "Ce qu'on retient le plus d'un équipier, c'est…", "Son talent pur", "Sa fiabilité", "B", "Rendre à l'heure et prévenir en cas de blocage est la qualité la plus recherchée.")
    )

    // Interactive Workshops
    val workshops = listOf(
        WorkshopDefinition(
            id = "atelier_budget",
            cahierId = 1,
            title = "Mon budget du mois",
            subtitle = "Remplis cette fiche avec tes chiffres réels (ou estimés) en Ariary.",
            quote = "Si le résultat A - B - C - D est positif : bravo, ajoute-le à l'épargne ! S'il est négatif, les envies doivent baisser.",
            calculationType = "BUDGET_CALC",
            fields = listOf(
                WorkshopField("recu_poche", "Argent de poche reçu", "ex: 15000", true, "Ar"),
                WorkshopField("recu_jobs", "Petit job / ventes", "ex: 10000", true, "Ar"),
                WorkshopField("recu_autres", "Cadeaux & autres", "ex: 5000", true, "Ar"),
                WorkshopField("epargne_obj", "Objectif de mon épargne", "ex: Achat calculatrice", false),
                WorkshopField("epargne_date", "Date visée pour l'objectif", "ex: 6 mois", false),
                WorkshopField("besoin_transport", "Transport (Taxi-be)", "ex: 6000", true, "Ar"),
                WorkshopField("besoin_repas", "Repas & fournitures scolaires", "ex: 5000", true, "Ar"),
                WorkshopField("besoin_tel", "Crédit tél. utile", "ex: 2000", true, "Ar"),
                WorkshopField("envie_sorties", "Sorties & loisirs", "ex: 4000", true, "Ar"),
                WorkshopField("envie_snacks", "Snacks & boissons", "ex: 3000", true, "Ar"),
                WorkshopField("envie_autres", "Vêtements & extras", "ex: 2000", true, "Ar")
            )
        ),
        WorkshopDefinition(
            id = "atelier_discipline",
            cahierId = 2,
            title = "30 jours de discipline",
            subtitle = "Choisis UNE habitude minuscule. Chaque jour tenu, coche la case.",
            quote = "Objectif : ne jamais laisser deux cases blanches à la suite. 20 cases sur 30 est déjà une immense réussite !",
            calculationType = "HABIT_30",
            fields = listOf(
                WorkshopField("habitude_nom", "Mon habitude choisie", "ex: 10 minutes de lecture chaque soir"),
                WorkshopField("habitude_heure", "À quelle heure ?", "ex: 21h30"),
                WorkshopField("habitude_lieu", "Où ?", "ex: Sur mon bureau"),
                WorkshopField("habitude_declencheur", "Juste après quoi ?", "ex: Après avoir éteint mon téléphone"),
                WorkshopField("habitude_recompense", "Ma récompense au 30e jour", "ex: Un bon livre ou une sortie spéciale")
            )
        ),
        WorkshopDefinition(
            id = "atelier_emotions",
            cahierId = 2,
            title = "Mon journal des émotions",
            subtitle = "Note une situation vécue, l'émotion ressentie et ce qui a aidé.",
            quote = "Observer sans juger suffit souvent à faire baisser la tension intérieure.",
            fields = listOf(
                WorkshopField("emo_situation", "La situation déclenchante", "ex: Remarque d'un camarade"),
                WorkshopField("emo_nom", "L'émotion ressentie (nom précis)", "ex: Colère, déception"),
                WorkshopField("emo_corps", "Sensation dans le corps", "ex: Gorge serrée, chaleur au visage"),
                WorkshopField("emo_intensite", "Intensité ressentie (sur 10)", "ex: 7", true, "/10"),
                WorkshopField("emo_aide", "Ce qui a aidé à apaiser", "ex: Prendre 6 expirations lentes dehors")
            )
        ),
        WorkshopDefinition(
            id = "atelier_offre_prix",
            cahierId = 3,
            title = "Mon offre et mon prix",
            subtitle = "Une compétence, un client type, une offre claire et un tarif calculé.",
            quote = "Un prix juste se calcule, il ne se devine pas.",
            calculationType = "RATE_CALC",
            fields = listOf(
                WorkshopField("comp_principale", "Ma compétence principale", "ex: Réalisation d'affiches graphiques"),
                WorkshopField("comp_preuve", "Preuve (projet ou travail déjà fait)", "ex: Affiche de l'épicerie du quartier"),
                WorkshopField("client_type", "Client type (qui et où ?)", "ex: Commerçants de ma ville"),
                WorkshopField("client_probleme", "Son problème précis", "ex: Pas de visibilité sur les réseaux"),
                WorkshopField("offre_nom", "Nom de l'offre", "ex: Pack Affiche + Visuel Réseaux"),
                WorkshopField("gains_voulus", "Revenu mensuel visé (A)", "ex: 120000", true, "Ar"),
                WorkshopField("frais_mensuels", "Frais mensuels (B)", "ex: 30000", true, "Ar"),
                WorkshopField("heures_mois", "Heures vendables par mois (C)", "ex: 28", true, "h"),
                WorkshopField("heures_offre", "Heures pour cette offre (D)", "ex: 4", true, "h")
            )
        ),
        WorkshopDefinition(
            id = "atelier_idee_page",
            cahierId = 4,
            title = "Mon idée en une page (Canvas)",
            subtitle = "Neuf cases pour tester la solidité de ton projet.",
            quote = "Une entreprise, c'est une solution que quelqu'un accepte de payer.",
            fields = listOf(
                WorkshopField("canvas_1_pb", "1. Le Problème (Qui souffre de quoi ?)", "ex: Les élèves manquent d'aide aux devoirs"),
                WorkshopField("canvas_2_client", "2. Mon Client Type (Nomme 3 personnes réelles)", "ex: Parents d'élèves de 6ème du quartier"),
                WorkshopField("canvas_3_actuel", "3. Ce qu'il fait aujourd'hui & coût", "ex: Rien ou cours particuliers trop chers"),
                WorkshopField("canvas_4_solution", "4. Ma Solution en une phrase", "ex: Séance d'exercices en petit groupe le samedi"),
                WorkshopField("canvas_5_mvp", "5. Mon Produit Minimum (Livrable en 7 jours)", "ex: Une première séance d'essai pour 3 élèves"),
                WorkshopField("canvas_6_diff", "6. Ce qui me rend différent", "ex: Explications calmes et méthodes visuelles"),
                WorkshopField("canvas_7_cout", "7. Mon Coût de Revient par unité", "ex: Photocopies + 2h de temps = 5 000 Ar"),
                WorkshopField("canvas_8_prix", "8. Mon Prix & Marge", "ex: Prix 8 000 Ar (Marge 3 000 Ar)"),
                WorkshopField("canvas_9_lieux", "9. Où trouver mes 3 premiers clients", "ex: Sortie de l'école le vendredi")
            )
        ),
        WorkshopDefinition(
            id = "atelier_semaine",
            cahierId = 6,
            title = "Ma semaine & Priorités",
            subtitle = "Place tes priorités fixes et stratégiques de la semaine.",
            quote = "Ce qui n'a pas de créneau n'existe pas.",
            fields = listOf(
                WorkshopField("prio_1", "Priorité 1 de la semaine", "ex: Préparer l'exposé d'histoire"),
                WorkshopField("prio_2", "Priorité 2 de la semaine", "ex: 3 séances de révision maths"),
                WorkshopField("prio_3", "Priorité 3 de la semaine", "ex: Séance de sport samedi matin"),
                WorkshopField("creneau_prio1", "Créneau réservé P1", "ex: Mardi 17h-18h30"),
                WorkshopField("creneau_prio2", "Créneau réservé P2", "ex: Lundi, Mercredi, Vendredi 18h"),
                WorkshopField("dimanche_bilan", "Bilan du dimanche : ce qui a fonctionné", "ex: 2/3 priorités tenues sans distraction")
            )
        ),
        WorkshopDefinition(
            id = "atelier_audit_num",
            cahierId = 7,
            title = "Mon audit numérique",
            subtitle = "Fais le tour de tes comptes et sécurise tes accès clés.",
            quote = "Qui tient ta messagerie tient tout le reste.",
            fields = listOf(
                WorkshopField("audit_2fa_mail", "Double vérification activée sur le mail ?", "ex: Oui, via Google Authenticator / SMS"),
                WorkshopField("audit_2fa_mobile", "Double vérification sur compte mobile money ?", "ex: Oui, code PIN secret non partagé"),
                WorkshopField("audit_mdp_gestion", "Mots de passe longs et uniques stockés où ?", "ex: Gestionnaire sécurisé"),
                WorkshopField("audit_backup", "Dernière sauvegarde photos & documents", "ex: Faite dimanche dernier"),
                WorkshopField("audit_action_1", "Action 1 à corriger cette semaine", "ex: Changer le mot de passe réutilisé sur 2 sites")
            )
        ),
        WorkshopDefinition(
            id = "atelier_boussole",
            cahierId = 8,
            title = "Ma boussole & Plan 10 ans",
            subtitle = "Une carte de ce que tu sais aujourd'hui sur toi pour trouver ta direction.",
            quote = "On trouve sa voie en essayant, pas en restant dans sa chambre.",
            fields = listOf(
                WorkshopField("bouss_aime", "Ce que j'aime faire (perte de notion du temps)", "ex: Résoudre des casse-têtes, expliquer"),
                WorkshopField("bouss_sait", "Ce que je sais bien faire (mes atouts)", "ex: Rigueur, calcul, écoute attentive"),
                WorkshopField("bouss_besoin", "Un problème du monde qui m'agace et que je veux aider à régler", "ex: L'accès aux outils numériques simples"),
                WorkshopField("bouss_essai", "Une expérience concrète à tenter d'ici 3 mois", "ex: Faire un stage d'une semaine dans un atelier"),
                WorkshopField("bouss_direction", "Ma direction actuelle en une phrase", "ex: Me former en gestion et informatique pour créer mon activité")
            )
        )
    )

    // Lexique Glossary by Cahier
    val lexiques = mapOf(
        1 to listOf(
            LexiqueItem("Budget", "Plan de répartition de l'argent reçu selon des pourcentages prédéfinis."),
            LexiqueItem("Revenu", "Argent qui entre : salaire, petit job, argent de poche, vente."),
            LexiqueItem("Dépense", "Argent qui sort pour acheter un bien ou un service."),
            LexiqueItem("Épargne", "Argent mis de côté pour plus tard et non dépensé immédiatement."),
            LexiqueItem("Intérêts composés", "Intérêts calculés aussi sur les intérêts déjà accumulés (effet boule de neige)."),
            LexiqueItem("Inflation", "Hausse générale et durable des prix qui réduit le pouvoir d'achat."),
            LexiqueItem("Prix unitaire", "Prix ramené au kilo ou au litre pour comparer objectivement les formats.")
        ),
        2 to listOf(
            LexiqueItem("Émotion", "Réaction rapide du corps et de l'esprit à une situation ; un signal utile."),
            LexiqueItem("Pensée automatique", "Phrase intérieure spontanée, souvent biaisée, qui conditionne l'émotion."),
            LexiqueItem("Assertivité", "Capacité à affirmer ses choix et dire non avec calme et respect."),
            LexiqueItem("Résilience", "Capacité à rebondir après un échec et à en tirer un apprentissage."),
            LexiqueItem("Discipline", "Habitude d'agir régulièrement, même en l'absence de motivation immédiate.")
        ),
        3 to listOf(
            LexiqueItem("Marché caché", "70 % des opportunités d'emploi qui ne sont jamais publiées en annonce."),
            LexiqueItem("Portfolio", "Sélection de réalisations concrètes qui prouvent tes compétences."),
            LexiqueItem("Méthode STAR", "Structure de réponse en entretien : Situation, Tâche, Action, Résultat."),
            LexiqueItem("Personal Branding", "Ce que les gens disent de ta fiabilité et de ton travail en ton absence."),
            LexiqueItem("Forfait", "Prix global et fixe convenu à l'avance pour un résultat défini.")
        ),
        4 to listOf(
            LexiqueItem("Produit Minimum (MVP)", "La plus petite version fonctionnelle livrable dès cette semaine."),
            LexiqueItem("Coût de revient", "Total des coûts directs + temps passé + part des charges fixes par unité."),
            LexiqueItem("Marge", "Différence entre le prix de vente et le coût de revient."),
            LexiqueItem("Trésorerie", "Argent liquide réellement disponible en caisse à l'instant T.")
        ),
        5 to listOf(
            LexiqueItem("Écoute active", "Écouter pour comprendre l'autre sans préparer sa propre réponse."),
            LexiqueItem("Reformulation", "Redire avec ses mots ce que l'autre a exprimé pour valider l'entente."),
            LexiqueItem("Analogie", "Image ou comparaison avec le quotidien pour faire comprendre une idée abstraite."),
            LexiqueItem("Malentendu", "Deux compréhensions différentes du même mot ou de la même consigne.")
        ),
        6 to listOf(
            LexiqueItem("Contrat", "Accord écrit qui engage juridiquement les parties signataires."),
            LexiqueItem("Franchise", "Part financière restant à ta charge lors d'un sinistre assuré."),
            LexiqueItem("Provision", "Somme mise de côté chaque mois pour lisser une dépense annuelle."),
            LexiqueItem("Hameçonnage", "Technique d'escroquerie imitant un organisme officiel pour voler tes données.")
        ),
        7 to listOf(
            LexiqueItem("Double vérification (2FA)", "Sécurité exigeant un mot de passe + un code temporaire."),
            LexiqueItem("Hallucination IA", "Réponse inventée par un modèle mais présentée avec certitude."),
            LexiqueItem("Bulle de filtre", "Enfermement algorithmique dans des contenus similaires à ses clics passés.")
        ),
        8 to listOf(
            LexiqueItem("Rappel actif", "Se tester de mémoire sans regarder le cours pour ancrer la rétention.", 8),
            LexiqueItem("Répétition espacée", "Réviser à intervalles croissants (J+1, J+7, J+30) contre l'oubli.", 8),
            LexiqueItem("Autonomie", "Capacité à chercher, décider et se corriger soi-même.", 8)
        )
    )

    val lexique: List<LexiqueItem> by lazy {
        lexiques.flatMap { (cahierId, items) ->
            items.map { it.copy(cahierId = cahierId) }
        }
    }

    val rulesToRemember = mapOf(
        1 to listOf(
            "1. Les besoins d'abord, les envies ensuite.",
            "2. Épargne 20 % dès que l'argent arrive.",
            "3. Note tes dépenses : la réalité surprend toujours.",
            "4. Compare trois prix avant un achat important.",
            "5. Si c'est trop beau, c'est faux."
        ),
        2 to listOf(
            "1. Une émotion est un signal, pas un ordre.",
            "2. Une pensée n'est pas un fait.",
            "3. Un « non » clair vaut mieux qu'un « oui » forcé.",
            "4. Rater, c'est apprendre — si on réessaie.",
            "5. Demander de l'aide est une force."
        ),
        3 to listOf(
            "1. Dis à trente personnes ce que tu cherches.",
            "2. Une compétence sans preuve est une opinion.",
            "3. Un prix se calcule, il ne se devine pas.",
            "4. Ne baisse jamais un prix sans retirer quelque chose.",
            "5. On rappelle les fiables, pas les brillants."
        ),
        4 to listOf(
            "1. Pars du problème, jamais de l'idée.",
            "2. Le seul vrai test, c'est quelqu'un qui paie.",
            "3. Compte ton temps dans le coût de revient.",
            "4. Chiffre d'affaires n'est pas bénéfice.",
            "5. Garder un client vaut mieux qu'en chercher dix."
        ),
        5 to listOf(
            "1. Ce qui compte, c'est ce que l'autre comprend.",
            "2. Trois secondes de silence font parler.",
            "3. Trois idées, pas douze.",
            "4. Un mot flou fabrique un malentendu.",
            "5. Une critique bien reçue est une information gratuite."
        ),
        6 to listOf(
            "1. On ne signe jamais ce qu'on n'a pas lu.",
            "2. Ce qui n'est pas écrit n'existe pas.",
            "3. Ce qui n'a pas de créneau ne se fera pas.",
            "4. L'urgence et le secret sont les marques de l'arnaque.",
            "5. Dans le doute, on ne partage pas."
        ),
        7 to listOf(
            "1. Qui tient ta messagerie tient tout le reste.",
            "2. Un code reçu par SMS ne se donne jamais.",
            "3. Une IA produit du plausible, pas du vrai.",
            "4. Ton fil est une sélection, pas le monde.",
            "5. Avant de croire : qui le dit, quand, pourquoi ?"
        ),
        8 to listOf(
            "1. Comprendre n'est pas savoir : teste-toi.",
            "2. Un problème inconnu se simplifie avant de se résoudre.",
            "3. Attendre a un coût, même s'il est invisible.",
            "4. Fini vaut mieux que parfait.",
            "5. On trouve sa voie en essayant, pas en réfléchissant."
        )
    )
}
