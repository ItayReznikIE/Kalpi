# Kalpi

Kalpi is a Java-based system simulating election processes within a polling station.  
It manages voters, candidate vote tickets, voting, and counting — offering a full simulation of the voting day in a structured and object-oriented manner.

## Features

- Reads input data about voters and candidates from text files
- Allows each voter to vote for a candidate using a vote ticket
- Sorts vote tickets based on voter age
- Counts votes for each candidate
- Displays final results
- Implements exception handling for missing or invalid vote tickets
- Uses custom data structures such as queues

## Technologies Used

- Java
- Eclipse IDE

## How to Run

1. Open the project in Eclipse  
2. Set `KalpiMain.java` as the entry point  
3. Ensure the input files `voters data.txt` and `id list.txt` are located under the `src/` directory  
4. Run `KalpiMain.java` and view the results in the console  

## Project Structure

- `src/` – source code directory  
  Core classes include:
  - `Voter.java`
  - `VoteTicket.java`
  - `VotesList.java`
  - `VotesCounter.java`
  - `SecurityGuard.java`
  - `KalpiMain.java`
  - and more...
- `bin/` – compiled class files
- `.settings/`, `.classpath`, `.project` – Eclipse project configuration

## Input Files

The program uses two input files:

### `voters data.txt`

Contains lines with details for each voter and candidate:

```
<ID> <Full Name> <Age> <City> <Years in City> <List Name> <Candidate Type>
```

**Example:**

```
123456789 John Smith 42 TelAviv 15 ListA Mayor
```

### `id list.txt`

Represents the vote ticket of each voter:

```
<Candidate ID> <Voter ID>
```

**Example:**

```
987654321 123456789
```

## Author

Itay Reznik
