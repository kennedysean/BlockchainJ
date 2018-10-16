import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Blockchain {

  private ArrayList<Block> blockchain;

  /**
   * Public constructor for a blockchain object.
   */
  public Blockchain() {
    this.blockchain = new ArrayList<>();
  }

  /**
   * Adds a block to the end of the block chain.
   * @param block  the block to add to the chain
   */
  public void add(Block block) {
    blockchain.add(block);
  }

  /**
   * Checks if {@code this} is a valid blockchain.
   * @return  true, if valid. otherwise, false
   */
  public boolean isValidBlockchain() {
    for (int i = 1; i < blockchain.size(); i++) {
      Block currentBlock = blockchain.get(i);
      Block previousBlock = blockchain.get(i - 1);
      // compare registered merkleRootHash and calculated merkleRootHash:
      if (!(currentBlock.isValid() && previousBlock.isValid())) {
        System.out.println("\nBlock " + (i + 1) + " hash has been altered.");
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the last block in {@code this}.
   * @return  the last block in the blockchain
   */
  public Block getLast() {
    return this.blockchain.get(blockchain.size() - 1);
  }

  /**
   * Creates a JSON representation of {@code this} and prints it to a file.
   * @return  the JSON string representation
   */
  public String toJson() {
    String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
    try {
      FileWriter jsonFile = new FileWriter("blockchain.json");
      jsonFile.write(blockchainJson);
      jsonFile.close();
    } catch (IOException e) {
      System.err.println("Error: Cannot write blockchain to JSON");
    }
    return blockchainJson;
  }
}
