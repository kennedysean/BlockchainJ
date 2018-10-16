import java.util.Date;

public class Block {

    private String data; // data in the block e.g. transactions, messages, etc.
    private String previousHash; // digital signature of previous block in the blockchain
    private String merkleRootHash; // digital signature of block
    private long timeStamp; // time stamp of block at moment of creation
    private int nonce = 0; // used for guessing target hash when mining a block
    private int difficulty; // number of leading zeros in block hash

    // Block constructor
    public Block(String data, String previous_hash, int difficulty){
        this.data = data;
        this.previousHash = previous_hash;
        this.timeStamp = new Date().getTime();
        this.difficulty = difficulty;
        this.merkleRootHash = doubleSha256();
    }

    public String doubleSha256() {
        /*Use all desired immutable parts of block to calculate encrypted
         *block hash using two iterations of the SHA256 algorithm.
         */
        String block_string = previousHash + timeStamp + nonce + data;
        String singleSha256 = Sha256.applySha256(block_string);
        return Sha256.applySha256(singleSha256);
    }

    public void mineBlock() {
        // create a string of zeros specified by difficulty
        String target = new String(new char[difficulty]).replace('\0', '0');
        // attempt to guess target hash until the block has been mined
        while(!merkleRootHash.substring(0, difficulty).equals(target)) {
            nonce++;
            merkleRootHash = doubleSha256();
        }
        System.out.println("Block successfully mined: " + merkleRootHash);
    }

    public String previous() {
        return this.previousHash;
    }

    public String hash() {
        return this.merkleRootHash;
    }


}