import java.util.Date;

public class Block {

    private String data; // data in the block e.g. transactions, messages, etc.
    private String previousHash; // digital signature of previous block in the blockchain
    private String merkleRootHash; // digital signature of block
    private long timeStamp; // time stamp of block at moment of creation
    private int nonce = 0; // used for guessing target hash when mining a block
    private int difficulty; // number of leading zeros in block hash

    /**
     * Constructor for a block object.
     * @param data  the data to be held in the block
     * @param previousHash  the hash of the previous block
     * @param difficulty    the number of leading zeros in the block hash
     */
    public Block(String data, String previousHash, int difficulty) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.difficulty = difficulty;
        this.merkleRootHash = doubleSha256();
    }

    /**
     * Use all desired immutable parts of block to calculate encrypted
     * block hash using two iterations of the SHA256 algorithm.
     * @return  the SHA256 hash of the block
     */
    public String doubleSha256() {
        /*Use all desired immutable parts of block to calculate encrypted
         *block hash using two iterations of the SHA256 algorithm.
         */
        String blockString = previousHash + timeStamp + nonce + data;
        String singleSha256 = Sha256.applySha256(blockString);
        return Sha256.applySha256(singleSha256);
    }

    /**
     * Mines {@code this} by guessing the target hash.
     */
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

    /**
     * Returns the previous block's hash.
     * @return  this.previousHash
     */
    public String previous() {
        return this.previousHash;
    }

    /**
     * Returns {@code this} hash.
     * @return  this.merkleRootHash
     */
    public String hash() {
        return this.merkleRootHash;
    }

    /**
     * Ensures the hash of {@code this} is equal to the SHA256 hash.
     * @return  true, if they are equal
     */
    public boolean isValid() {
        return this.merkleRootHash.equals(this.doubleSha256());
    }


}