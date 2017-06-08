package org.davidmoten.rx.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;

import com.github.davidmoten.guavamini.Preconditions;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public final class UpdateBuilder extends ParametersBuilder<UpdateBuilder>
        implements DependsOn<UpdateBuilder> {

    static final int DEFAULT_BATCH_SIZE = 1;

    final String sql;
    final Flowable<Connection> connections;
    private final Database db;
    Flowable<?> dependsOn;
    int batchSize = DEFAULT_BATCH_SIZE;

    public UpdateBuilder(String sql, Flowable<Connection> connections, Database db) {
        super(sql);
        this.sql = sql;
        this.connections = connections;
        this.db = db;
    }

    @Override
    public UpdateBuilder dependsOn(Flowable<?> flowable) {
        Preconditions.checkArgument(dependsOn == null, "dependsOn can only be set once");
        dependsOn = flowable;
        return this;
    }

    public UpdateBuilder batchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    /**
     * Returns a builder used to specify how to process the generated keys
     * {@link ResultSet}. Not all jdbc drivers support this functionality and
     * some have limitations in their support (h2 for instance only returns the
     * last generated key when multiple inserts happen in the one statement).
     * 
     * @return a builder used to specify how to process the generated keys
     *         ResultSet
     */
    public ReturnGeneratedKeysBuilder returnGeneratedKeys() {
        Preconditions.checkArgument(batchSize == 1,
                "Cannot return generated keys if batchSize > 1");
        return new ReturnGeneratedKeysBuilder(this);
    }

    public Flowable<Integer> counts() {
        return startWithDependency(Update.create(connections.firstOrError(),
                super.parameterGroupsToFlowable(), sql, batchSize, true).dematerialize());
    }

    <T> Flowable<T> startWithDependency(Flowable<T> f) {
        if (dependsOn != null) {
            return dependsOn.ignoreElements().andThen(f);
        } else {
            return f;
        }
    }

    public TransactedUpdateBuilder transacted() {
        return new TransactedUpdateBuilder(this, db);
    }

    public Single<Tx<?>> transaction() {
        return transacted().tx();
    }

    public Completable complete() {
        return counts().ignoreElements();
    }

}
