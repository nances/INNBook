package com.kaqi.niuniu.ireader.model.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.kaqi.niuniu.ireader.model.bean.ReviewBookBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "REVIEW_BOOK_BEAN".
*/
public class ReviewBookBeanDao extends AbstractDao<ReviewBookBean, String> {

    public static final String TABLENAME = "REVIEW_BOOK_BEAN";

    /**
     * Properties of entity ReviewBookBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, String.class, "_id", true, "_ID");
        public final static Property Cover = new Property(1, String.class, "cover", false, "COVER");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Site = new Property(3, String.class, "site", false, "SITE");
        public final static Property Type = new Property(4, String.class, "type", false, "TYPE");
    }


    public ReviewBookBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ReviewBookBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"REVIEW_BOOK_BEAN\" (" + //
                "\"_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: _id
                "\"COVER\" TEXT," + // 1: cover
                "\"TITLE\" TEXT," + // 2: title
                "\"SITE\" TEXT," + // 3: site
                "\"TYPE\" TEXT);"); // 4: type
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"REVIEW_BOOK_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ReviewBookBean entity) {
        stmt.clearBindings();
 
        String _id = entity.get_id();
        if (_id != null) {
            stmt.bindString(1, _id);
        }
 
        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(2, cover);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String site = entity.getSite();
        if (site != null) {
            stmt.bindString(4, site);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(5, type);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ReviewBookBean entity) {
        stmt.clearBindings();
 
        String _id = entity.get_id();
        if (_id != null) {
            stmt.bindString(1, _id);
        }
 
        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(2, cover);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String site = entity.getSite();
        if (site != null) {
            stmt.bindString(4, site);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(5, type);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ReviewBookBean readEntity(Cursor cursor, int offset) {
        ReviewBookBean entity = new ReviewBookBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // cover
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // site
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // type
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ReviewBookBean entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setCover(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSite(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ReviewBookBean entity, long rowId) {
        return entity.get_id();
    }
    
    @Override
    public String getKey(ReviewBookBean entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ReviewBookBean entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
