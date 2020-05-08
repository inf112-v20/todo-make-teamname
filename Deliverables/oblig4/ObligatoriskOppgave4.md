# Obligatorisk Oppgave 4

## Deloppgave 1 - Team og prosjekt
*   *Rollene* i teamet har fungert veldig bra, og har egentlig ikke blitt endret siden de ble satt. Vi er fortsatt fornøyd med rollene og hvordan de har fungert - vi deler en del på rollene slik at teamet kan få jobbe med det de har lyst til, og dette har fungert.

*   *Erfaringer team-messig*: For vår gruppe har vi funnet ut at vi får gjort mindre nå med nedstengningen enn når vi kunne møtes fysisk.
    
*   *Retrospektiv*: Nå som vi har jobbet remote en stund og virkelig har fått føle på hvordan det er å jobbe slik.
Vårt team har kjent på dette og har funnet ut at vi foretrekker å jobbe fysisk sammen, og føler at vi fikk holdt oppe trykket i arbeidet bedre da vi kunne møtes.
Vi hadde en tilvennings-periode men har nå blitt mer vant til å jobbe remote og føler det går bedre nå enn det gikk i starten.
I forhold til nedstengningen så har kommunikasjonen og gruppedynamikken fungert like bra om ikke bedre - vi kommuniserer mye oftere nå via Slack og Discord.
Vi føler at nedstengningen har påvirket vår gruppe negativt når det gjelder fremdriften og arbeidsinnsatsen i prosjektet, arbeidslysten er ikke like høy og har ikke fått til like mye på disse siste ukene enn på de tidligere ukene.
Dette kan også ha en sammenheng med at mye av de store funksjonalitetene som måtte implementeres ble gjort veldig tidlig og det har vært perioder der
vi bare har gjort mye finnpuss og utvidet litt og litt på det vi allerede har gjort. Slik fremgang kan føles ut som at
har mye mindre påvirkning på prosjektets helhet, og derfor virke treigt og uviktig å implementere. Men, da vi satt oss ned sammen og skrev ned alle de tingene vi hadde gjort siden forrige leveranse ble vi overrasket over hvor mye vi faktisk hadde fullført.
Det vi kunne gjort annerledes med tanke på denne obligen var at vi kunne møtes digitalt oftere enn vi gjorde.

*   Siden dette var siste innlevering i prosjektet så gikk fokuset vårt angående prioritering av oppgaver på å ferdigstille programmet vårt, squashe bugs og fikse andre feil som vi har funnet når vi playtestet spillet.
Rett og slett gjøre produktet vi har produsert mest mulig polert til leveranse.

*   Skjermbilde fra projectboard(Trello) ligger vedlagt i oblig4 mappen


## Deloppgave 2 - Krav
*   Siden forrige gang har vi fått laget et til spillbrett, i tillegg til en meny hvor host av spillet kan velge hvilket map som skal spilles.
Vi har også implementert en kombinert log og chatboks - loggen viser hva som skjer med de ulike robotene i spillet, om de går på et bånd eller om de blir truffet av laser.
I chatten kan spillerne skrive inn meldinger som kan bli sett av alle i gamet. Vi har nå en options meny der brukeren kan endre skjermstørrelsen på spillet, et fullt re-design av kortene for å vise mer informasjon og forbedre lesbarhet.
Det ble også implementert en power down knapp, i tillegg til at nå kan robotene skubbe hverandre hvis de treffer på hverandre.
Vi har ikke implementert noen særlig store endringer til spillet men heller en god del bugfixes fra forrige iterasjon.

*   Vi føler nå at vi har et MVP, og det krysser av alle kravene vi har satt for at vi regner det som MVP.

*   MVP: vi satt opp at vi ville at en spiller skulle kunne spille en fullstendig runde med alle spill elementene som runder, kort og brett-elementer. 
Vi har oppdatert dette kravet til å inkludere å kunne spille 4 spillere sammen, og dette fungerer. 
Tilleggsfunksjonalitet vi har lagt til er: chatboks / log, options meny der skjermstørrelse kan endres, en level select der vi har lagt til et til kart - 2 totalt.

*   Vi valgte å ikke implementere program cards.


####   Krav: Card
    *   Brukerhistorie: Som spiller vil jeg ha kort som forteller mer om kortet.
    *   Akseptansekriterie: Kot skal vise prioritering, om det er valgt, en kort beskrivelse, et ikon, om det er valgt, og rekkefølgen de er valgt i.
    *   Arbeidsoppgaver: Lage nye teksturer, og vise de ulike aspektene oppå teksturen.

####	Krav: Card Hand
    *   Brukerhistorie: Som spiller vil jeg at jeg skal få kort ut i fra hvor mange damagetokens jeg har mistet.
    *   Akseptansekriterie: Passe på at hver spiller har riktig mengde kort, og at de vises korrekt
    *   Arbeidsoppgaver: Vise kort på skjermen ut i fra hvor mange kort som er på hånd, samt sjekke hvor mange kort hver spiller kan ha før det blir delt ut.

####	Krav: Options Menu
    *   Brukerhistorie: Som spiller vil jeg ha muligheten til å endre skjermstørrelsen.
    *   Akseptansekriterie: Spillvinduet skal fungere for ulike skjermstørrelser.
    *   Arbeidsoppgaver: Refaktorere deler av spillet som er avhengig av en spesifikk skjermstørrelse til å akseptere ulike skjermstørrelser.

####	Krav: Power Down Button
    *   Brukerhistorie: Kunne skru av roboten i starten av hver runde for å få tilbake liv.
    *   Akseptansekriterie: Robot skrur seg av, spiller ikke kort, kan fortsatt bli skubbet og påvirket av andre roboter. Får fullt liv på starten av neste runde, visst den ikke har blitt ødelagt i mellomtiden
    *   Arbeidsoppgaver: Implementere en knapp som skrur av roboten og sender den beskjeden til alle spillere.

####	Krav: Chat Log
    *   Brukerhistorie: Kunne lese og sende meldinger til hverandre i spillet. Kunne lese hva som har skjedd i spillet.
    *   Akseptansekriterie: Kunne sende meldinger som alle får, kunne lett forstå visst brettet påvirker roboten din.
    *   Arbeidsoppgaver: Lage input til chat, lage log. Sette det sammen med server.

####	Krav: Robot Collision
    *   Brukerhistorie: Roboter som kolliderer skubber hverandre
    *   Akseptansekriterie: Roboter blir skubbet rett i forhold til reglene i spillet
    *   Arbeidsoppgaver: Implementere logikk for kollisjon i TurnHandler, og skrive grundige tester.

####	Krav: Level Select
    *   Brukerhistorie: Som spiller vil jeg få opp en ny meny med banene jeg kan velge å spille på.
    *   Akseptansekriterie: Menyen skal vise nivåene man kan velge mellom, og brettet skal få beskjed om å oppdatere kartet basert på hvilket brett man velger. Når valget er gjort skal også serveren få beskjed slik at alle som joiner ender opp på samme brett.
    *   Arbeidsoppgaver: Implementere knapper for banene (+ tilbakeknapp) en kan scrolle gjennom med piltastene og velge med enter. Vise bildet av nivået en har highlighted før en velger det. Oppdatere server til å kunne ta imot oppdatering av hvilket brett en skal spille.
     			       
*   Bugs: Ett tilfelle der host ikke kunne ha 4 pleyrs tilkoblet, funket når vi byttet hvem som hostet,
    lurer på om det er relatert til ruter som blir brukt.
 
 
## Deloppgave 3 - Produkeleveranse og kodekvalitet
*   Hvordan prosjektet kjøres er forklart i README.md 

*   Prosjektet har blitt testet på både Windows og OSX, men skal også fungere på Linux (her har testene kjørt).<br><br>

Manuelle tester:
*    Velge forskjellige menyer med piltaster i MainMenu,<br> visst du kommer til bunns, så går du til toppen igjen.
*   Visst du endrer skjermstørrelse i optionsMenu så endres skjermstørrelsen
*   Får bilde representasjon av brettet du skal spille på når du velger brett
*   Grønn hake kommer opp når en spiller har gjort seg klar i lobby
*   Kan skrive i textfieldet på JoinGameMenu
*   Når en spiller vinner spillet så går spillet til Win Screen
*   Får selvmordstanker når planen din ikke fungerer 
*	Hvis roboten kommer oppå et flagg så vil det registrere ved at det kommer opp en melding i log, i tillegg til å oppdatere flag counter ved siden av spillerne på venstre side av spillet.
*	Når melding skrives inn i chatteboksen og trykkes ENTER så vil meldingen dukke opp i chatten.
*	Kort blir korrekt markert når de blir trykket på ved å vise en gul ramme rundt, og det fungerer også å trykke igjen på samme kort for å av-velge det.
*	Kort blir spilt i riktig rekkefølge i forhold til sin prioriterings verdi
*	Skade ikonet går fra grå til gul når du tar skade fra laser, og går tilbake til grå når du gjenoppliver eller bruker “shutdown” knappen og når du står på reparerings ikonet.