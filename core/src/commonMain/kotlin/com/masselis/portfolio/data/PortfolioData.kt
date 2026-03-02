package com.masselis.portfolio.data

import com.masselis.portfolio.data.Skill.Level.Confirmed
import com.masselis.portfolio.data.Skill.Level.Expert
import com.masselis.portfolio.data.Skill.Level.Initiated
import com.masselis.portfolio.data.Skill.Level.Mastery
import com.masselis.portfolio.data.Tag.Android
import com.masselis.portfolio.data.Tag.Architecture
import com.masselis.portfolio.data.Tag.Compose
import com.masselis.portfolio.data.Tag.Multiplatform
import com.masselis.portfolio.data.Tag.Resume
import com.masselis.portfolio.data.TimelineEntry.Moment
import com.masselis.portfolio.data.TimelineEntry.Pending
import com.masselis.portfolio.data.TimelineEntry.Range
import kotlinx.datetime.YearMonth
import portfolio.core.generated.resources.Res
import portfolio.core.generated.resources.cubeinstore
import portfolio.core.generated.resources.decathlon
import portfolio.core.generated.resources.embipos
import portfolio.core.generated.resources.kadiska_banner
import portfolio.core.generated.resources.kadiska_logo
import portfolio.core.generated.resources.vivawallet

public object PortfolioData {
    public val skills: Set<Skill> = setOf(
        Skill("Kotlin", Mastery, Android, Multiplatform, Compose, Resume),
        Skill("iOS / Swift", Expert, Multiplatform, Resume),
        Skill("Coroutines/Flow", Mastery, Android, Multiplatform, Resume),
        Skill("Jetpack Compose", Expert, Android, Multiplatform, Compose, Resume),
        Skill("SwiftUI", Confirmed, Multiplatform, Resume),
        Skill("Android SDK", Mastery, Android, Compose, Resume, Architecture),
        Skill(
            "Compose Multiplatform",
            Expert,
            Android,
            Multiplatform,
            Compose,
            Resume,
            Architecture
        ),
        Skill("Clean Architecture", Expert, Android, Multiplatform, Resume, Architecture),
        Skill("Fragment/XML", Expert, Android, Resume),
        Skill("ViewModel MVVM/MVI", Mastery, Android, Multiplatform, Resume, Architecture),
        Skill("Gradle Framework", Expert, Android, Multiplatform, Resume, Architecture),
        Skill("Dagger/Hilt/Koin/Metro", Mastery, Multiplatform, Resume),
        Skill("CI/CD Bitrise/Actions", Confirmed, Android, Multiplatform, Resume, Architecture),
        Skill("Testing Mockk/Mockito", Expert, Android, Multiplatform, Architecture),
        Skill("Crash monitoring", Expert, Android, Resume, Architecture),
        Skill("RxJava", Mastery, Android),
        Skill("Objective-C", Confirmed),
        Skill("Java", Confirmed, Android),
        Skill("Rust", Initiated),
        Skill("Go", Initiated),
        Skill("Home Assistant", Expert),
    )

    public val timelineEntries: List<TimelineEntry> = listOf(
        TimelineEntry(
            Moment(YearMonth(2009, 6)),
            "Mon premier smartphone Android",
            "Sans le savoir, j'ai acheté l'HTC Dream, le tout premier téléphone commercialisé sous Android, qui tracera ma carrière pour les années à venir",
        ),
        TimelineEntry(
            Moment(YearMonth(2010, 10)),
            "Mes premiers developpements",
            "Système domotique X10 en C++",
        ),
        TimelineEntry(
            Range(
                YearMonth(2013, 7),
                YearMonth(2016, 9)
            ),
            "Début de carrière professionnelle sous iOS et Android",
            "Dès le début, les enjeux sont fort avec, entre autres, la refonte Android et iOS de l'application Chronodrive",
        ),
        TimelineEntry(
            Range(
                YearMonth(2016, 10),
                YearMonth(2020, 11),
            ),
            "Ma première startup Equisense",
            "Rejoint en early stage juste après leur levée de fond, j'ai conçu et développé from-scratch l'application iOS et Android, ensuite rejoint par 3 autres développeurs. Pendant 4 ans j'ai vécu l'effervescence et l'implication hors normes que représente le travail pour une startup avec laquelle on partage tout",
        ),
        TimelineEntry(
            Pending(YearMonth(2020, 12)),
            "Début du freelancing chez Decathlon",
            "Après 10 ans développement et une solide expérience, je décide, un peu par hasard, d'entrer dans le monde du Freelancing en commençant chez Decatlon"
        ),
    )

    private val skillMap = skills.associateBy { it.name }
    public val projects: List<Project> = listOf(
        Project(
            logo = Res.drawable.decathlon,
            image = Res.drawable.cubeinstore,
            title = "CubeInStore",
            bulletPoints = listOf(
                "100k utilisateurs mensuels",
                "+600k lignes de code",
                "+20 développeurs",
                "+50 pays"
            ),
            fullDescription = "Conception, réalisation et maintenance de multiples sujets à haut niveau technique intégrés les équipes de développeurs(euses) au sein de la plus grosse application Android de Decathlon.\n" +
                    "Dans l'équipe \"Platform\", j'ai travaillé sur des enjeux techniques transverses afin de répondre à des problématiques techniques complexe et/ou profondes.\n" +
                    "\n" +
                    "Éclatement d'une base de code unique en plusieurs répertoires associé à chaque équipe:\n" +
                    " - Optimise le temps de compilation pour chaque développeur et pour la C.I.\n" +
                    " - Redonne de l'ownership de code et de l'autonomie à chaque équipe\n" +
                    " - Structure et normalise le code inter-équipe\n" +
                    " - Création d'une application coquille scalable qui ne contient de base que le login, la home et les settings\n" +
                    " - Normalisation des tests\n" +
                    " - Normalisation de l'IOC Koin\n" +
                    " - Normalisation du code avec, entre autres, des règles KtLint custom\n" +
                    " - 100% Kotlin\n" +
                    " - C.I. Bitrise puis Github Action\n" +
                    " - Framework Gradle\n" +
                    " - Google Artifact Registry\n" +
                    "\n" +
                    "Refonte du process d'authentification:\n" +
                    " - 100% Kotlin\n" +
                    " - 100% Coroutine + Flow\n" +
                    " - Android Custom Tab\n" +
                    " - Thread safe\n" +
                    " - Énormément de tests pour simuler tous les cas de figure possible comme une application en background, une rotation, un changement d'app, plusieurs ouvertures en simultané, ouvertures en séquentiel, etc..\n" +
                    "\n" +
                    "Application et création d'un plugin pour contrôler l’offuscation de code avec R8:\n" +
                    " - Framework Gradle\n" +
                    " - Optimisation de la C.I. Bitrise\n" +
                    " - Décompilation d'APK avec jadx\n" +
                    "\n" +
                    "Optimisation de la configuration Gradle:\n" +
                    " - Publication et écriture des plugins\n" +
                    " - Réécriture des scripts préexistants",
            skills = listOf(
                skillMap["Kotlin"]!!,
                skillMap["Coroutines/Flow"]!!,
                skillMap["Jetpack Compose"]!!,
                skillMap["Android SDK"]!!,
                skillMap["Clean Architecture"]!!,
                skillMap["Gradle Framework"]!!,
                skillMap["Dagger/Hilt/Koin/Metro"]!!,
                skillMap["CI/CD Bitrise/Actions"]!!,
                skillMap["Testing Mockk/Mockito"]!!,
                skillMap["Crash monitoring"]!!
            )
        ),
        Project(
            logo = Res.drawable.decathlon,
            image = Res.drawable.vivawallet,
            title = "Express Payment",
            bulletPoints = listOf(
                "Une caisse Decathlon comme simple application",
                "Haute disponibilité et de qualité élevée",
                "Taux de conversion supérieur à une caisse classique",
            ),
            fullDescription = "Conception et mis en place une refonte majeure d'une fonctionnalité permettant au collaborateur Decathlon d'encaisser un client sans passer en caisse.\n" +
                    "Cette fonctionnalité dispose de:\n" +
                    " - Composition panier\n" +
                    " - Identification client\n" +
                    " - Paiement par carte bancaire.\n" +
                    "Tout était pris en charge, le stock, la facture, les portiques de sécurités, la carte de fidélité et surtout le paiement avec une carte bancaire via NFC.\n" +
                    "\n" +
                    "Afin de garantir un haut niveau de disponibilité, l'intégralité de la transaction était synchronisé en temps réel sur Firebase. Chaque transaction peut être complété soit par le collaborateur, soit par le serveur, la fonctionnalité s'adapte aux nouvelles informations suivant le principe du 2-way binding.\n" +
                    "\n" +
                    "Enjeux:\n" +
                    "- Haute résilience du traitement des paiements, qu'ils soient en échec ou pas, chaque transaction aboutissait sur un résultat concret et actionnable pour le collaborateur dans un contexte où de nombreux services sont appelés en simultanés\n" +
                    "- Forte stabilité y compris dans des environnements difficiles comme une connexion internet très faible\n" +
                    "- Simple d'utilisation, clair, concis et efficace. Le teammate était amené à réaliser ces opérations devant un client, il devait donc pouvoir utiliser la fonctionnalité d'une main avec une faible attention\n" +
                    "\n" +
                    "Cette fonctionnalité fait partie d'une plus grande application interne à Decatlon:\n" +
                    "- 100% Clean architecture\n" +
                    "- 100% Kotlin\n" +
                    "- 100% Flow\n" +
                    "- 100% Compose\n" +
                    "- Dagger puis Koin\n" +
                    "- Firebase Realtime Database pour le 2-way binding\n" +
                    "- Usage du SDK VivaWallet pour la lecture des informations de la carte bancaire\n" +
                    "- A/B Testing avec Firebase\n" +
                    "- Crash report avec Crashlytics",
            skills = listOf(
                skillMap["Kotlin"]!!,
                skillMap["Coroutines/Flow"]!!,
                skillMap["Jetpack Compose"]!!,
                skillMap["Android SDK"]!!,
                skillMap["Clean Architecture"]!!,
                skillMap["ViewModel MVVM/MVI"]!!,
                skillMap["Dagger/Hilt/Koin/Metro"]!!,
                skillMap["Testing Mockk/Mockito"]!!,
                skillMap["Crash monitoring"]!!
            )
        ),
        Project(
            logo = Res.drawable.kadiska_logo,
            image = Res.drawable.kadiska_banner,
            title = "Kadiska Android",
            bulletPoints = listOf(
                "B2B",
                "Analyse réseau low-level",
                "Services en arrière-plan complexes",
                "Fort enjeux business"
            ),
            fullDescription = "Étude de la faisabilité du projet avec un PoC contenant 3 approches différentes\n" +
                    "Définition de l’architecture\n" +
                    "Force de proposition sur la valeur ajoutée des données orientées mobile\n" +
                    "Identification et contournement des limitations systèmes\n" +
                    "Mise au point de la machine d'intégration et de livraison continue\n" +
                    "Étude et configuration d'un logiciel de gestion de parc de smartphone\n" +
                    "Écriture de scripts de livraison avancés à destination de plusieurs Play Stores Entreprise\n" +
                    "Monté en compétence des collaborateurs confirmés\n" +
                    "Banc de test et benchmark de smartphones Android\n" +
                    "\n" +
                    "Application 100% background dédié à l'analyse réseau entre le smartphone et un serveur donné:\n" +
                    "- 100% Clean architecture\n" +
                    "- Multi-modules\n" +
                    "- 100% Kotlin Coroutine + Flow\n" +
                    "- IOC avec Dagger2\n" +
                    "- Base de donnée avec SQLDelight\n" +
                    "- Intégration, compilation et exécution de sources C/C++ basées sur Traceroute debian\n" +
                    "- Analyse de trames réseau IMPC/UDP et ipv4/ipv6 extraites du smartphone\n" +
                    "- Extraction des données de connexion du téléphone (Wifi, 4G, 5G, dBm, BSSID, ip)\n" +
                    "- Configuration entreprise avec VMware Workspace One\n" +
                    "- Intégration continue avec Github Actions\n" +
                    "- Livraison automatique sur plusieurs Play Store Entreprise en simultané\n" +
                    "\n" +
                    "SDK minimaliste avec une empreinte mémoire réduite pour accompagner l'application:\n" +
                    "- Code en JAVA au lieu du Kotlin\n" +
                    "- Aucune librairie externe n'est utilisée",
            skills = listOf(
                skillMap["Kotlin"]!!,
                skillMap["Java"]!!,
                skillMap["Coroutines/Flow"]!!,
                skillMap["Android SDK"]!!,
                skillMap["Dagger/Hilt/Koin/Metro"]!!,
                skillMap["Fragment/XML"]!!,
                skillMap["CI/CD Bitrise/Actions"]!!,
                skillMap["Rust"]!!,
            )
        ),
        Project(
            logo = Res.drawable.decathlon,
            image = Res.drawable.embipos,
            title = "EmbiSDK et RfidLink",
            bulletPoints = listOf(
                "Kotlin et Compose Multiplatform",
                "Point d'entré unique de tous les lecteurs RFID",
                "Critique pour le business de Decathlon",
                "Utilsé tous les jours par des milliers de collaborateurs",
            ),
            fullDescription = "EmbiSDK: Prend en charge les lecteurs interne Decathlon (ex-embisphere). Ce SDK est livré avec une application Desktop et Android :\n" +
                    "- 100% clean architecture\n" +
                    "- Kotlin multiplatform à destination d'Android et du Desktop\n" +
                    "- Compose Multiplaform pour créer, à partir d'un code unique, une interface Desktop et Android\n" +
                    "- 100% Kotlin coroutine + flow\n" +
                    "- Manipulation bas niveau de la mémoire pour parser et sérialiser un flux d'octets\n" +
                    "- Génération de classes Kotlin à partir d'une spécification json\n" +
                    "- IOC avec Koin\n" +
                    "- Quality gate avec Sonarqube et Jacoco\n" +
                    "- Documentation complète pour l'intégrateur du SDK avec javadoc\n" +
                    "- Build et livraison automatique avec github actions sur un serveur maven\n\n" +
                    "RfidLink: SDK unifié qui prend en charge plusieurs fabricants de lecteurs RFID:\n" +
                    "- 100% clean architecture\n" +
                    "- 4 adapters (coroutine, RxJava2, RxJava3 et callback)\n" +
                    "- 100% Kotlin avec RxJava3\n" +
                    "- IOC avec Dagger2\n" +
                    "- Quality gate avec Sonarqube et Jacoco\n" +
                    "- BDD avec Room\n" +
                    "- Documentation complète pour l'intégrateur du SDK avec javadoc\n" +
                    "- 3 applications en Kotlin et 1 application en Java \"sample\" pour guider les intégrateurs dans la bonne manière d'exploiter le SDK\n" +
                    "- Build et livraison automatique avec Bitrise sur un serveur maven",
            skills = listOf(
                skillMap["Kotlin"]!!,
                skillMap["Java"]!!,
                skillMap["Coroutines/Flow"]!!,
                skillMap["Android SDK"]!!,
                skillMap["Dagger/Hilt/Koin/Metro"]!!,
                skillMap["Fragment/XML"]!!,
                skillMap["CI/CD Bitrise/Actions"]!!,
                skillMap["Rust"]!!,
            )
        ),
    )

    public val contacts: List<ContactInfo> = listOf(
        ContactInfo("R\u00E9seau Professionnel", "linkedin.com/in/vincentmasselis", "LinkedIn"),
        ContactInfo("Projets & Code", "github.com/RxVincent", "GitHub"),
        ContactInfo("R\u00E9seau Social", "bsky.app/profile/rxvincent.bsky.s", "Bluesky"),
        ContactInfo("Contact Direct", "vincent@rxvincent.com", "Email"),
    )
}
