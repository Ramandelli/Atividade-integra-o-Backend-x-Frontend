package br.edu.umfg.secaudit.ordermanagement.sequence;

public class NoSequence implements Sequence{
    @Override
    public long nextValue() {
        return 0;
    }
}
