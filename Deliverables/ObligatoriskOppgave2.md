# Obligatorisk Oppgave 2

## Deloppgave 1 - Prosjekt og prosjektstruktur
*   (Hva innebærer de ulike rollene (faktisk))
*   **Erfaringer**: Vi har merket at det å planlegge krav og hva de innebærer på forhånd tar tid, men vi merker at ved å 
gjøre dette så har vi et mye klarere bilde av akkurat hva vi skal gjøre og hvordan.
*   Valgene vi har tatt synes vi har vært gode og har passet med hva vi tenkte vi kunne få til i denne sprinten.
*   **Gruppedynamikk**: er god, vi samarbeider godt og føler at alle bidrar med noe.
*   **Kommunikasjon**: fungerer veldig bra, hvis det er noe som må avklares er alle lett tilgjengelig på Slack.
*   **Retrospektiv**: Vi planlagte å jobbe mer strukturert og forebyggende med å skrive opp krav og mer konkret hvilke arbeidsoppgaver som
må gjøres for å få til dette kravet. Vi har blitt mye bedre med dette siden forrige sprint, og merket hva det har gjort
med jobbingen, det har vært lettere å se hva som må implementeres for å oppnå kravet. Vi føler at vi kan bli enda bedre
med denne måten å jobbe på og dette vil være et mål fram mot neste sprint.
Å bruke møter til bare å planlegge detaljene for neste leveranse før vi begynner å kode har revolusjonert arbeidsprosessen.
Da alle begynte å arbeide hver for seg ble det mye lettere å gjennomføre kravene ettersom vi alle hadde det samme bildet
 i hodet av hvordan det skulle henge sammen for den endelige leveransen.
*   **Forbedringspunkter**: Vi ble enige om at det vi kunne bli bedre på til neste sprint er testing og dokumentasjon. 
Få på plass tester tidligere enn vi har gjort nå og å være flinkere til å dokumentere viktig / kompleks kode så det er 
enklere for de andre på gruppen å skjønne hvordan koden fungerer.
*   **Stor Forskjell i antall commits**: Gjennom denne obligatoriske innleveringen var Elias på ferie og fikk derfor
ikke gjort så mye, men resten av gruppen har veldig jevnt med commits.

## Deloppgave 2 - Krav
*   Nå fremover har vi prioriert å få brettet og brettets elementer på plass, i tillegg til å få implementert noen
statiske kort som vi kan bruke til å teste funksjonalitet med. Fram til neste levering har vi lyst å få på plass liv og 
damage til roboten, i tillegg til å få kortene nærmere ferdig implementasjon ved å legge til kort-deck.
* Hovedkrav som del av MVP (og hvorfor): 
*   Vi har prioritert å implementere kort i denne sprinten, og har begynt med å få på plass noen statiske kort som kan
programmere roboten. Vi valgte å gå videre med kort først siden vi følte det var et viktig steg å få på plass i prosjektet.
* Hva vi har gjort siden forrige gang:
    *   tekst
*   Bugs:
    *   

### Krav: Kort
*   Brukerhistorier:
    *   Som spiller skal jeg kunne se hvilken funksjon et kort har ved å se på det
    *   Som spiller skal jeg kunne trykke på et kort for å velge det
    *   Som spiller skal jeg kunne se om jeg har valgt et kort
    *   Som spiller skal jeg kunne av-velge et kort
    *   Som spiller skal jeg kunne si at jeg er ferdig med å velge å si meg klar
    *   Som spiller skal jeg kunne se robotens liv på skjermen
    *   Som spiller skal jeg kunne se robotens damage på skjermen
*   Akseptansekriterier:
    *   Kort skal kunne velges og avvelges
    *   Kort skal ha en funksjon som representerer hva det utfører i spillet
*   Arbeidsoppgaver:
    *   Ha en verdi som forteller hvilket type kort det er.

### Krav: Spiller UI
*   Brukerhistorier:
    *   Som spiller skal man se 9 kort på skjermen som tilhører spilleren
    *   Som spiller skal man se en powerdown knapp
    *   Som spiller skal man se en representasjon av hvor mye liv roboten til spilleren har - kalt Life Tokens
    *   Som spiller skal man se en representasjon av hvor mye skade roboten til spilleren har tatt - kalt Damage Tokens
*   Akseptansekriterier:
    *   Visuell representasjon av kort 
    *   Visuell representasjon av "klar", brukeren låst inn sitt valg av kort
    *   Visuell representasjon av  hvor mye liv og skade roboten har og har tatt.
*   Arbeidsoppgaver:
    *   Tegne kort
    *   Tegne markering av kort
    *   Tegne powerdown knapp 
    *   Tegne liv til robot
    *   Tegne skade til robot

### Krav: Spillfasene
*   Brukerhistorier:
    *   Som spiller kunne velge 5 kort og plassere dem i ønsket rekkefølge
    *   Som spiller kunne trykke på en knapp som sier at spilleren er klar og ferdig med å velge kort
    *   Som spiller kunne se at spillet utføres på den måten kortene tilsier og gjøre det 5 ganger (5 kort)
*   Akseptansekriterier:
    *   Velge og avvelge kort ved å trykke kortene på skjermen
    *   "Låse inn" valg av kort ved å trykke på ready knappen på skjermen
    *    Se roboten bevege seg etter hva kortene programmerte den til
*   Arbeidsoppgaver:
    *   Registrere input og ved valg av kort gi beskjed om at du er klar
    *   Programmere rekkefølgen i spillfasene
    *   Programmere objekt klasser i spillfasene
    *   Programmere hvilke konsekvenser kort og objekter får for spillet

## Deloppgave 3
Kode
*   **Bygges**: Prosjektet bygges på vanlig vis
*   **Testes**: vi har brukt for det meste JUnit for å teste koden, men begynt nå å bruke Mockito slik at klasser kan bli 
testet "i vakum", for seg selv.
*   **Kjøres**: RoboRally kjøres av Main() klassen.
    *   Obs: Roboten begynner å se mot EAST, vi har ikke implementert retningen visuelt enda.