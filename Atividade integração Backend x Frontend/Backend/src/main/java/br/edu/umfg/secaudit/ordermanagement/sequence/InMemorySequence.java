package br.edu.umfg.secaudit.ordermanagement.sequence;

public class InMemorySequence implements Sequence {

    private long sequence = 0;

    @Override
    public long nextValue() {
        sequence++;
        return sequence;
    }

}
