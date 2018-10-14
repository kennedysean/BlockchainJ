import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class Blockchain {
    // initialize blockchain
    public static ArrayList<Block> blockchain = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // create Genesis Block - the first block in the blockchain
        Block genesisBlock = new Block ("Genesis block", "0", 5);
        blockchain.add(genesisBlock);
        System.out.println("Mining genesis block... ");
        blockchain.get(0).mineBlock();
        // add the second block to the blockchain
        blockchain.add(new Block("*Transaction list*",blockchain.get(blockchain.size()-1).merkleRootHash,5));
        System.out.println("Mining second block... ");
        blockchain.get(1).mineBlock();
        // third block
        blockchain.add(new Block("*Transaction list*", blockchain.get(blockchain.size()-1).merkleRootHash,5));
        System.out.println("Mining third block... ");
        blockchain.get(2).mineBlock();

        int i = 3;
        System.out.println("Would you like to mine another block? ");
        Scanner input = new Scanner(System.in);
        String answer = input.nextLine();
        while(answer.toLowerCase().equals("yes")) {
            blockchain.add(new Block("*Transaction list*", blockchain.get(i-1).merkleRootHash,5));
            System.out.println("Mining new block... ");
            blockchain.get(i).mineBlock();
            i++;
            System.out.println("Would you like to mine another block? ");
            answer = input.nextLine();
        }

        // verify that the data in the blockchain has not been altered
        if (areHashesValid()){
            System.out.println("\nBLOCKCHAIN IS VALID.");
        } else {
            System.out.println("\nBLOCKCHAIN IS NOT VALID.");
        }

        // convert the blockchain into a JSON file, print the blockchain, and save the file
        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        FileWriter jsonFile = new FileWriter("blockchain.json");
        jsonFile.write(blockchainJson);
        jsonFile.close();
        System.out.println("\nBlockchain: ");
        System.out.println(blockchainJson);
    }

    // loops through blocks and compares hashes to ensure blockchain is valid
    public static Boolean areHashesValid() {
        Block currentBlock;
        Block previousBlock;
        // loop through blockchain to check hashes
        for (int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            // compare registered merkleRootHash and calculated merkleRootHash:
            if (!(currentBlock.merkleRootHash.equals(currentBlock.doubleSha256()) &&
                    previousBlock.merkleRootHash.equals(currentBlock.previousHash))) {
                System.out.println("\nBlock " + Integer.toString(i+1) + " hash has been altered.");
                return false;
            }
        }
        return true;
    }
}
