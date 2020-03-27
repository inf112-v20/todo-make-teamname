# Obligatorisk Oppgave 3

## Deloppgave 1 - Team og prosjekt
*   Vi synes fortsatt rollene fungerer bra og har sin hensikt.
*   Erfaring team-messing/prosjektmetodikk: Etter at skolen stengte har vi fått kjenne på hvordan det er å bare jobbe remote,
og hvilke utfordringer dette kan medføre. Vi har fått bekreftet at arbeidsmetiden slik vi jobber fortsatt fungerer, det er 
enkelt å holde styr på hva de ulike i teamet jobber med til enhver tid med Trello tavlen vår.
*   Det vi kan gjøre annerledes videre for å forbedre teamwork:
*   Retrospektiv: Siden forrige gang har vi sett veldig mye hvordan vi kan refaktorere koden. Game klassen ble veldig stor til slutt
og det var vanskelig å holde oversikt på alt som skjedde. Dokumentasjon av metoder og klasser var heller ikke så bra så det har
vi fokusert mye på i denne sprinten. Dette øker leselighet og gjør det lettere for andre å sette seg inn i koden. En stor del av game ble plassert
i en ny klasse som bare holder styr på turns (TurnHandler). Ellers arbeidet generelt har gått bra - vi har ikke hatt noe problem
med å møtes digitalt.
*   3 forbedringspunkter fra retrospektiv
*   Hvordan vi har prioritert opgpavene fremover, legg ved sc av trello
*   Gruppedynamikk og kommunikasjonen: Gruppedynamikken fungerer fortsatt veldig bra, vi har ikke hatt noen problem med å holde
kontakten etter skolen stengte. Møter har vi holdt på Discord hver fredag, i tillegg til andre dager hvis det er noe teamet hadde lyst å diskutere.
På Slack oppdaterer vi de andre i teamet hvis vi implementerer større endringer.

## Deloppgave 2 - Krav
*   Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang
*   Brukerhistorier - Akseptansekriterier - arbeidsoppgaver

####   Krav: **Vegg**


    *   Brukerhistorie: Som spiller skal jeg kunne se vegger visuelt representert på brettet
    *   Akseptansekriterie: Vegg skal vøre en del av brettet
    *   Arbeidsoppgaver: Tegne vegg på brettet - brettet er et bilde. Tegne inn tegn i tekstkart for at spillet skal "se" veggene.
        
    *   Brukerhistorie: Som robot skal jeg kunne bevege meg mot en vegg og bli stoppet fra å bevege meg gjennom den
    *   Akseptansekriterie: Vegg stopper robotens bevegelse
    *   Arbeidsoppgaver: Opprette en wall klasse, legge til vegg i itemFactory, implementere vegg sjekk når roboten flyttes.
    
    *   Brukerhistorie: Som laser skal min stråle bli stoppet av vegger 
    *   Akseptansekriterie: Vegg stopper laser stråle fra å gå gjennom den
    *   Arbeidsoppgaver: legge til en sjekk at hvis laseren treffer på en vegg så stopper den.
####   Krav: **Lan**


    *   Menyer
	    *   Brukerhistorie: Vil kunne velge om jeg skal være vert eller koble til eit spill.
		    *   Akseptansekrav
			    *   Kunne sette opp ein server og få tilgang til ip addresse til den serveren.
			    *   Kunne koble til server ved å få ein ip addresse 
			Ikke ta hensyn til:
			    *   Mislykket tilkobling
			    *   Koble til igjen etter å ha mistet tilkoblingen
		    *   Arbeidskrav
			    *   Vise i lobby og i game en oppdatert liste overnavn som er koblet til.
        *  Brukerhistorie: Vil kunne velge brukernavn og kunne se hvem som har koblet seg til server.
		    *   Akseptansekrav
			    *   Kunne ta inn eit brukernavn per spiller og vise det på skjermen
			Ikke ta hensyn til:
			    *   Utseende
			    *   Skrivefeil eller ordbruk i brukernavn
		    *   Arbeidskrav
			    *   Vise i lobby og i game en oppdatert liste overnavn som er koblet til.
 
    *   Spill
        *   Brukerhistorie: Vil kunne spille med flere personer
            *   Akseptansekrav
			    *   Få spill fasene til å fungere for opp til 4 spillere
			Ikke ta hensyn til:
			    *   Power down
			    *   Vil alltid få samme 5 kort
		    *   Arbeidskrav
			    *   Sende og motta kort
			    *   Sørge for at rett brikke blir spilt i forhold til hvem som spilte kortet    
*   Hvilke hovedkrav vi anser som MVP og hvorfor
*   Bugs:
    *   Gå mot vegg stopper robot, men hvis prøver samme move igjen så vil den ignorere den bevegelsen mot veggen å prøve neste i rekken.

## Deloppgave 3 - Produkeleveranse og kodekvalitet
*   Kjøre på win osx linux
*   Klassediagram
*   Testdekning vektlegges
*   Manuell testing -> lever beskrivelse av hvordan testen foregår slik at gruppeleder kan utføre testen selv
*   Hvis stor forskjell commits - forklar hvorfor