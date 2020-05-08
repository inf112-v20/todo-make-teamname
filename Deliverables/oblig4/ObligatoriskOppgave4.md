# Obligatorisk Oppgave 4

## Deloppgave 1 - Team og prosjekt
*   *Rollene* i teamet har fungert veldig bra, og har egentlig ikke blitt endret siden de ble satt. Vi er fortsatt førnøyd med rollene
og hvordan de har fungert - vi deler en del på rollene slik at teamet kan få jobbe med det de har lyst til, og dette har fungert.
*   *Erfaringer team-messig*: For vår gruppe har vi funnet ut at vi får gjort mindre nå med nedstengningen enn når vi kunne møtes fysisk.
    
*   *Retrospektiv*: Nå som vi har jobbet remote en stund og virkelig har fått føle på hvordan det er å jobbe slik.
Vårt team har kjent på dette og har funnet ut at vi foretrekker å jobbe fysisk sammen, og føler at vi fikk holdt oppe trykket i arbeidet bedre da vi kunne møtes.
Vi hadde en tilvennings-periode men har nå blitt mer vant til å jobbe remote og føler det går bedre nå enn det gikk i starten.
I forhold til nedstengningen så har kommunikasjonen og gruppedynamikken fungert like bra om ikke bedre - vi kommuniserer mye oftere nå via Slack og Discord.
Vi føler at nedstengningen har påvirket vår gruppe negativt når det gjelder fremdriften og arbeidsinnsatsen i prosjektet, arbeidslysten er ikke like høy og har ikke fått til like mye på disse siste ukene enn på de tidligere ukene.
Dette kan også ha en sammenheng med at mye av de store funkjonalitetene som måtte implementeres ble gjort veldig tidlig og det har vært perioder der
vi bare har gjort mye finnpuss og utvidet litt og litt på det vi allerede har gjort. Slik fremgang kan føles ut som at
har mye mindre påvirkning på prosjektets helhet, og derfor virke treigt og uviktig å implementere.
Det vi kunne gjort annerledes med tanke på denne obligen var at vi kunne møtes digitalt oftere enn vi gjorde.
*   Siden dette var siste innlevering i prosjektet så gikk fokuset vårt angående prioritering av oppgaver på å ferdigstille programmet vårt, squashe bugs og fikse andre feil som vi har funnet når vi playtestet spillet.
Rett og slett gjøre produktet vi har produsert mest mulig polert til leveranse.
*   [] Skjermbilde av trello - passe på at det er fra når vi er klar til å levere inn

## Deloppgave 2 - Krav
*   Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang:

*   Siden forrige gang har vi fått laget et til spillbrett, i tillegg til en meny hvor host av spillet kan velge hvilket map som skal spilles.

Vi har ikke implemert noen særlig store endringer til spillet men heller en god del bugfixes fra forrige iterasjon.
*   Vi føler nå at vi har et MVP, og det krysser av alle kravene vi har satt for at vi regner det som MVP.

*   Hvilke hovedkrav vi anser som MVP:
*   OBS: "Dersom dere har oppgaver dere skal starte med, hvor dere har laget brukerhistorier osv så kan dere ta med disse og"
*   Vi valgte å ikke implementere program

####   Krav: **Sett inn**

    *   Brukerhistorie:
    *   Akseptansekriterie: 
    *   Arbeidsoppgaver:
        			       
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