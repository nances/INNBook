package com.kaqi.niuniu.ireader.model.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.kaqi.niuniu.ireader.model.bean.DownloadTaskBean;
import com.kaqi.niuniu.ireader.model.bean.AuthorBean;
import com.kaqi.niuniu.ireader.model.bean.BookCommentBean;
import com.kaqi.niuniu.ireader.model.bean.CollBookBean;
import com.kaqi.niuniu.ireader.model.bean.BookHelpsBean;
import com.kaqi.niuniu.ireader.model.bean.ReviewBookBean;
import com.kaqi.niuniu.ireader.model.bean.BookRecordBean;
import com.kaqi.niuniu.ireader.model.bean.BookHelpfulBean;
import com.kaqi.niuniu.ireader.model.bean.BookChapterBean;
import com.kaqi.niuniu.ireader.model.bean.BookReviewBean;

import com.kaqi.niuniu.ireader.model.gen.DownloadTaskBeanDao;
import com.kaqi.niuniu.ireader.model.gen.AuthorBeanDao;
import com.kaqi.niuniu.ireader.model.gen.BookCommentBeanDao;
import com.kaqi.niuniu.ireader.model.gen.CollBookBeanDao;
import com.kaqi.niuniu.ireader.model.gen.BookHelpsBeanDao;
import com.kaqi.niuniu.ireader.model.gen.ReviewBookBeanDao;
import com.kaqi.niuniu.ireader.model.gen.BookRecordBeanDao;
import com.kaqi.niuniu.ireader.model.gen.BookHelpfulBeanDao;
import com.kaqi.niuniu.ireader.model.gen.BookChapterBeanDao;
import com.kaqi.niuniu.ireader.model.gen.BookReviewBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig downloadTaskBeanDaoConfig;
    private final DaoConfig authorBeanDaoConfig;
    private final DaoConfig bookCommentBeanDaoConfig;
    private final DaoConfig collBookBeanDaoConfig;
    private final DaoConfig bookHelpsBeanDaoConfig;
    private final DaoConfig reviewBookBeanDaoConfig;
    private final DaoConfig bookRecordBeanDaoConfig;
    private final DaoConfig bookHelpfulBeanDaoConfig;
    private final DaoConfig bookChapterBeanDaoConfig;
    private final DaoConfig bookReviewBeanDaoConfig;

    private final DownloadTaskBeanDao downloadTaskBeanDao;
    private final AuthorBeanDao authorBeanDao;
    private final BookCommentBeanDao bookCommentBeanDao;
    private final CollBookBeanDao collBookBeanDao;
    private final BookHelpsBeanDao bookHelpsBeanDao;
    private final ReviewBookBeanDao reviewBookBeanDao;
    private final BookRecordBeanDao bookRecordBeanDao;
    private final BookHelpfulBeanDao bookHelpfulBeanDao;
    private final BookChapterBeanDao bookChapterBeanDao;
    private final BookReviewBeanDao bookReviewBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        downloadTaskBeanDaoConfig = daoConfigMap.get(DownloadTaskBeanDao.class).clone();
        downloadTaskBeanDaoConfig.initIdentityScope(type);

        authorBeanDaoConfig = daoConfigMap.get(AuthorBeanDao.class).clone();
        authorBeanDaoConfig.initIdentityScope(type);

        bookCommentBeanDaoConfig = daoConfigMap.get(BookCommentBeanDao.class).clone();
        bookCommentBeanDaoConfig.initIdentityScope(type);

        collBookBeanDaoConfig = daoConfigMap.get(CollBookBeanDao.class).clone();
        collBookBeanDaoConfig.initIdentityScope(type);

        bookHelpsBeanDaoConfig = daoConfigMap.get(BookHelpsBeanDao.class).clone();
        bookHelpsBeanDaoConfig.initIdentityScope(type);

        reviewBookBeanDaoConfig = daoConfigMap.get(ReviewBookBeanDao.class).clone();
        reviewBookBeanDaoConfig.initIdentityScope(type);

        bookRecordBeanDaoConfig = daoConfigMap.get(BookRecordBeanDao.class).clone();
        bookRecordBeanDaoConfig.initIdentityScope(type);

        bookHelpfulBeanDaoConfig = daoConfigMap.get(BookHelpfulBeanDao.class).clone();
        bookHelpfulBeanDaoConfig.initIdentityScope(type);

        bookChapterBeanDaoConfig = daoConfigMap.get(BookChapterBeanDao.class).clone();
        bookChapterBeanDaoConfig.initIdentityScope(type);

        bookReviewBeanDaoConfig = daoConfigMap.get(BookReviewBeanDao.class).clone();
        bookReviewBeanDaoConfig.initIdentityScope(type);

        downloadTaskBeanDao = new DownloadTaskBeanDao(downloadTaskBeanDaoConfig, this);
        authorBeanDao = new AuthorBeanDao(authorBeanDaoConfig, this);
        bookCommentBeanDao = new BookCommentBeanDao(bookCommentBeanDaoConfig, this);
        collBookBeanDao = new CollBookBeanDao(collBookBeanDaoConfig, this);
        bookHelpsBeanDao = new BookHelpsBeanDao(bookHelpsBeanDaoConfig, this);
        reviewBookBeanDao = new ReviewBookBeanDao(reviewBookBeanDaoConfig, this);
        bookRecordBeanDao = new BookRecordBeanDao(bookRecordBeanDaoConfig, this);
        bookHelpfulBeanDao = new BookHelpfulBeanDao(bookHelpfulBeanDaoConfig, this);
        bookChapterBeanDao = new BookChapterBeanDao(bookChapterBeanDaoConfig, this);
        bookReviewBeanDao = new BookReviewBeanDao(bookReviewBeanDaoConfig, this);

        registerDao(DownloadTaskBean.class, downloadTaskBeanDao);
        registerDao(AuthorBean.class, authorBeanDao);
        registerDao(BookCommentBean.class, bookCommentBeanDao);
        registerDao(CollBookBean.class, collBookBeanDao);
        registerDao(BookHelpsBean.class, bookHelpsBeanDao);
        registerDao(ReviewBookBean.class, reviewBookBeanDao);
        registerDao(BookRecordBean.class, bookRecordBeanDao);
        registerDao(BookHelpfulBean.class, bookHelpfulBeanDao);
        registerDao(BookChapterBean.class, bookChapterBeanDao);
        registerDao(BookReviewBean.class, bookReviewBeanDao);
    }
    
    public void clear() {
        downloadTaskBeanDaoConfig.clearIdentityScope();
        authorBeanDaoConfig.clearIdentityScope();
        bookCommentBeanDaoConfig.clearIdentityScope();
        collBookBeanDaoConfig.clearIdentityScope();
        bookHelpsBeanDaoConfig.clearIdentityScope();
        reviewBookBeanDaoConfig.clearIdentityScope();
        bookRecordBeanDaoConfig.clearIdentityScope();
        bookHelpfulBeanDaoConfig.clearIdentityScope();
        bookChapterBeanDaoConfig.clearIdentityScope();
        bookReviewBeanDaoConfig.clearIdentityScope();
    }

    public DownloadTaskBeanDao getDownloadTaskBeanDao() {
        return downloadTaskBeanDao;
    }

    public AuthorBeanDao getAuthorBeanDao() {
        return authorBeanDao;
    }

    public BookCommentBeanDao getBookCommentBeanDao() {
        return bookCommentBeanDao;
    }

    public CollBookBeanDao getCollBookBeanDao() {
        return collBookBeanDao;
    }

    public BookHelpsBeanDao getBookHelpsBeanDao() {
        return bookHelpsBeanDao;
    }

    public ReviewBookBeanDao getReviewBookBeanDao() {
        return reviewBookBeanDao;
    }

    public BookRecordBeanDao getBookRecordBeanDao() {
        return bookRecordBeanDao;
    }

    public BookHelpfulBeanDao getBookHelpfulBeanDao() {
        return bookHelpfulBeanDao;
    }

    public BookChapterBeanDao getBookChapterBeanDao() {
        return bookChapterBeanDao;
    }

    public BookReviewBeanDao getBookReviewBeanDao() {
        return bookReviewBeanDao;
    }

}
