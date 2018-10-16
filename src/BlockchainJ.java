import java.util.Scanner;

public class BlockchainJ {

    /**
     * The main method for mining blocks.
     * @param args  command-line arguments
     */
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();

        // create Genesis Block - the first block in the blockchain
        Block genesisBlock = new Block("Genesis block", "0", 5);
        blockchain.add(genesisBlock);
        System.out.println("Mining genesis block... ");
        blockchain.getLast().mineBlock();
        // add the second block to the blockchain
        blockchain.add(new Block("*Transaction list*",blockchain.getLast().hash(),5));
        System.out.println("Mining second block... ");
        blockchain.getLast().mineBlock();
        // third block
        blockchain.add(new Block("*Transaction list*", blockchain.getLast().hash(),5));
        System.out.println("Mining third block... ");
        blockchain.getLast().mineBlock();

        Scanner input = new Scanner(System.in);
        System.out.print("Would you like to mine another block? ");
        while (input.nextLine().toLowerCase().equals("yes")) {
            blockchain.add(new Block("*Transaction list*", blockchain.getLast().hash(),5));
            System.out.println("Mining new block... ");
            blockchain.getLast().mineBlock();
            System.out.println("Would you like to mine another block? ");
        }

        // verify that the data in the blockchain has not been altered
        if (blockchain.isValidBlockchain()) {
            System.out.println("\nBLOCKCHAIN IS VALID.");
        } else {
            System.out.println("\nBLOCKCHAIN IS NOT VALID.");
        }

        // convert the blockchain into a JSON file, print the blockchain, and save the file
        String blockchainJson = blockchain.toJson();
        System.out.println("\nBlockchain: ");
        System.out.println(blockchainJson);
    }

}
