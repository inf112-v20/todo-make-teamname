# Obligatorisk Oppgave 1

## Kort beskrivelse av programmet
Et program som kan spille RoboRally opp til 4 personer over LAN.

## Kompetanse
Fag som alle i gruppen har tatt:
INF100, INF101, INF102, MNF130.

Erik:
*  Litt LibGdx erfaring, et tidligere prosjekt.
*  Andre året datavitenskap (INF214, MAT121, MAT221, STAT110)

Kristoffer:
*  Andre året på datateknologi (INF115, INF122, DAT103)
*  Har litt LibGdx erfaring, jobbet med et prosjekt tidligere.

Mads:
*  
*  

Elias:
*  Andre året på datasikkerhet (DAT103, INF142, INF143)

Tord:
*  Andre året på datasikkerhet (DAT103, INF142, INF143)
*  Tidligere erfaring med spill har vært oppgaver fra INF100 og INF101


## Roller
*  Teamleader -Kristoffer
*  Logikk - Erik
*  Frontend - Elias
*  Input - Mads
*  LAN ansvarlig - Erik
*  Test/QA ansvarlig - Tord
*  Kundekontakt - Elias
*  Dokumentasjons ansvarlig - Tord
*  Design ansvarlig - Mads
*  Grafikk ansvarlig - Kristoffer
*  AI-ansvarlig

Hvorfor vi valgte disse rollene:
Tanken bak rollene var å skape en løs idé om hvilke felter hver enkelt var mest interessert i å arbeide som og fordele 
ut overordna ansvar i de ulike delene av programutviklingen.
Vi ble også enige om at selv om det ble delt ut ulike ansvarsområder, så må hver enkelt fortsatt skrive kode, 
dokumentere og lage tester uansett hva de holder på med. Det er det generelle arbeidet, og rollene er bare for å vite 
hvordan vi ønsker å fordele det.


## Prosess- og prosjektplan
*  Som prosjektmetodikk tenker vi først og fremst å bruke elementer fra Kanban der vi har en kontinuerlig flyt av arbeid.
Som verktøy for dette tar vi i bruk Trello. Grunnen til at vi velger å jobbe på denne måten er at det blir enkelt å 
holde følge på hva som blir gjort akkurat nå, og hvem som holder på med det. Oppgaver kan bli gitt til andre medlemmer 
og vi holder det åpent for å bytte mellom oppgaver.
*  Vi tenker at vi vil jobbe testbasert slik at vi alltid har kontroll over at ny kode vil fungere når det implementeres.
*  I denne første perioden vil vi få oversikt over prosjektet, koordinere hvordan vi vil jobbe sammen, og få satt opp 
programmer og tavler for videre utvikling. Vi vil også prøve å sette opp arbeidsoppgaver i tavlen vår (Trello) av ulike 
elementer som vi har lyst å implementere. Møter vil vi ha hver fredag. Hvis vi føler at vi trenger mer tid øker vi møter
 til 2 ganger i uken. Kommunikasjonen oss imellom foregår på Slack.
*  Til prosjekt-board bestemte vi oss for å bruke Trello, og dette var fordi flere i gruppen har tidligere erfaring med 
dette programmet, i tillegg til at vi synes det var godt egnet til å få oversikt over oppgaver som skal bli gjort. 
Vi har satt det opp lik en Kanban tavle slik: Backlog - TODO - In Progress -
Need Tweaking og Done.
*  Oppfølging av oppgaver og at teamet gjør fremgang har teamleader fått ansvar for. 
Status quo er at gitte arbeidsoppgaver skal gjennomføres før neste møte.


## Spesifikasjon
*  Målet for applikasjonen er å lage en fungerende versjon av brettspillet RoboRally, der første robot som klarer å besøke alle checkpoint-flagget vinner spillet. Spillerne skal få utdelt tilfeldige kort (Program card) hver runde med instruksjoner for hva roboten din kan gjøre. Deretter skal en velge fem av ni kort i rekkefølgen en ønsker roboten skal gjennomføres i.
*  Systemkrav
  *  Representere spillbrett
  *  Representere brettelementer på brettet (hull, vegger, samlebånd, flagg, tannhjul)
  *  Representere spiller(e) som en robot
  *  Avslutte spillet (quit hvis noe går galt)
  *  Vinne spillet (Første til å besøke alle flagg)
  *  Besøke flagg
  *  Robot kan ta skade (laser, hull)
  *  Representere kort
  *  Dele ut tilfeldige kort i en runde
  *  Justere kort utifra skade på robot
  *  Representere laser som fungerer i henhold til vegger og roboter
  *  Robot kan dytte annen robot
  *  Powerdown funksjon 
  *  Samlebånd beveger seg i henhold til runder
  *  Roboter kan bli flyttet av samlebånd & andre roboter
  *  Robot kan bli rotert av tannhjul
  *  Robot kan dø
  *  Gjennomføre en runde basert på prioriterng av elementer og kort
  *  LAN spill med opp til 4 personer 
  *  Velge ulike brett
 
*  Prioritert liste over krav til første iterasjon:
  *  Representere spillbrett
  *  Representere en robot på brettet (som kan plasseres med mus)
   