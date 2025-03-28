package com.isylph.basis.repository.diff;

import com.isylph.basis.domain.domain.mark.Aggregate;
import com.isylph.basis.domain.domain.mark.Identifier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SnapshotUtils {

    public static <T extends Aggregate<ID>, ID extends Identifier> T snapshot(T aggregate)
            throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(aggregate);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (T) ois.readObject();
    }
}
