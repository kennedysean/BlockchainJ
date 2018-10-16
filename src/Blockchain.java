import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Blockchain {

  private ArrayList<Block> blockchain;

  public Blockchain() {
    this.blockchain = new ArrayList<>();
  }

  public void add(Block block) {
    blockchain.add(block);
  }

  public Block get(int idx) {
    if (idx < blockchain.size() && idx >= 0) {
      return blockchain.get(idx);
    }

    throw new Error("Error: Index is out of range");
  }

  public boolean isValid() {
    for (int i = 1; i < blockchain.size(); i++) {
      Block currentBlock = blockchain.get(i);
      Block previousBlock = blockchain.get(i-1);
      // compare registered merkleRootHash and calculated merkleRootHash:
      if (!(currentBlock.hash().equals(currentBlock.doubleSha256()) &&
          previousBlock.hash().equals(currentBlock.previous()))) {
        System.out.println("\nBlock " + (i+1) + " hash has been altered.");
        return false;
      }
    }
    return true;
  }

  public Block getLast() {
    return this.blockchain.get(blockchain.size() - 1);
  }

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
