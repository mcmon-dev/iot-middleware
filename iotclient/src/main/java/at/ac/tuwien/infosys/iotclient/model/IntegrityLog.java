package at.ac.tuwien.infosys.iotclient.model;

import java.math.BigInteger;

public class IntegrityLog extends LogMessage {

    private String transactionHash;
    private long tsHash;
    private long tsFile;
    private long hashFileDelay;
    private BigInteger blockNumber;
    private String blockHash;
    private boolean isRemoved;
    private long commitTs;
    private long blockTs;

    public IntegrityLog(String transactionHash, Message message, long tsHash, long tsFile, BigInteger blockNumber, String blockHash, boolean isRemoved) {
        super(message, Channel.INTEGRITY, tsFile - message.getTimestamp());
        this.transactionHash = transactionHash;
        this.tsHash = tsHash;
        this.tsFile = tsFile;
        this.blockNumber = blockNumber;
        this.blockHash = blockHash;
        this.isRemoved = isRemoved;

        hashFileDelay = tsFile - tsHash;

    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public long getTsHash() {
        return tsHash;
    }

    public long getTsFile() {
        return tsFile;
    }

    public long getHashFileDelay() {
        return hashFileDelay;
    }

    public BigInteger getBlockNumber() {
        return blockNumber;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public long getCommitTs() {
        return commitTs;
    }

    public void setCommitTs(long commitTs) {
        this.commitTs = commitTs;
    }

    public long getBlockTs() {
        return blockTs;
    }

    public void setBlockTs(long blockTs) {
        this.blockTs = blockTs;
    }
}
