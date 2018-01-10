package com.xyj.chapter_2.manualbinder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.xyj.chapter_2.aidl.Book;

import java.util.List;

/**
 * Created by xuyongjun on 2018/1/11.
 */

public interface IBookManager extends IInterface {

    static final String DESCRIPTOR = "com.xyj.chapter_2.manualbinder.IBookManager";

    static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    public java.util.List<Book> getBookList() throws android.os.RemoteException;

    public void addBook(Book book) throws android.os.RemoteException;

    public abstract static class BookManagerImpl extends Binder implements IBookManager {

        public BookManagerImpl() {
            this.attachInterface(this, IBookManager.DESCRIPTOR);
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_getBookList: {
                    data.enforceInterface(DESCRIPTOR);
                    java.util.List<com.xyj.chapter_2.aidl.Book> _result = this.getBookList();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                }
                case TRANSACTION_addBook: {
                    data.enforceInterface(DESCRIPTOR);
                    com.xyj.chapter_2.aidl.Book _arg0;
                    if ((0 != data.readInt())) {
                        _arg0 = com.xyj.chapter_2.aidl.Book.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    this.addBook(_arg0);
                    reply.writeNoException();
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        static class Proxy implements IBookManager {

            IBinder mRemote;

            public Proxy(IBinder mRemote) {
                this.mRemote = mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public List<Book> getBookList() throws RemoteException {
                //传递的参数（有参数的话）
                android.os.Parcel _data = android.os.Parcel.obtain();
                //服务端处理完返回的数据
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.util.List<com.xyj.chapter_2.aidl.Book> _result;
                try {
                    //用于区分接口
                    _data.writeInterfaceToken(DESCRIPTOR);
                    //发起RPC(远程过程调用)请求,进入到上面的onTransact()方法，_reply中就有数据了
                    mRemote.transact(IBookManager.TRANSACTION_getBookList, _data, _reply, 0);
                    //这边判断是否异常，如果有异常就不会往下执行了
                    _reply.readException();
                    //从_reply中将数据生成对应的返回数据，至此，一次请求就完成了
                    _result = _reply.createTypedArrayList(com.xyj.chapter_2.aidl.Book.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public void addBook(Book book) throws RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if ((book != null)) {
                        _data.writeInt(1);
                        book.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    mRemote.transact(IBookManager.TRANSACTION_addBook, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }
        }

    }

}
