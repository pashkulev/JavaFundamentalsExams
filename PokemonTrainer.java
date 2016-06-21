package lesson01_DefiningClasses.Problem08_PokemonTrainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class PokemonTrainer {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        LinkedHashMap<String, Trainer> trainers = new LinkedHashMap<>();

        while (true) {
            String[] tokens = reader.readLine().split("\\s+");
            if (tokens[0].equals("Tournament")) break;

            String trainerName = tokens[0];
            if (!trainers.containsKey(trainerName)) {
                trainers.put(trainerName, new Trainer(trainerName));
            }

            String pokemonName = tokens[1];
            String pokemonElement = tokens[2];
            int pokemonHealth = Integer.valueOf(tokens[3]);

            Pokemon pokemon = new Pokemon(pokemonName, pokemonElement, pokemonHealth);
            trainers.get(trainerName).pokemons.add(pokemon);
        }

        while (true) {
            String command = reader.readLine().trim();
            if (command.equals("End")) break;

            for (Map.Entry<String, Trainer> trainer : trainers.entrySet()) {
                HashSet<Pokemon> pokemons = trainer.getValue().pokemons;
                boolean foundElement = false;
                for (Pokemon pokemon : pokemons) {
                    if (pokemon.element.equals(command)) {
                        foundElement = true;
                        break;
                    }
                }

                if (foundElement) {
                    trainer.getValue().badges++;
                } else {
                    for (Pokemon pokemon : pokemons) {
                        pokemon.health -= 10;
                        if (pokemon.health <= 0) {
                            pokemons.remove(pokemon);
                        }
                    }
                }
            }
        }

        trainers.entrySet().stream()
                .sorted((t1,t2) -> Integer.compare(t2.getValue().badges, t1.getValue().badges))
                .forEach(trainer -> System.out.printf("%s %s %s%n",
                        trainer.getKey(),
                        trainer.getValue().badges,
                        trainer.getValue().pokemons.size()));
    }
}

class Trainer {
    String name;
    int badges;
    HashSet<Pokemon> pokemons;

    public Trainer(String name) {
        this.name = name;
        this.badges = 0;
        this.pokemons = new HashSet<>();
    }
}

class Pokemon {
    String name;
    String element;
    int health;

    public Pokemon(String name, String element, int health) {
        this.name = name;
        this.element = element;
        this.health = health;
    }
}