package dao;
import java.util.List;

public  interface GenericDAO <T> {
  void add(T entity);
   T get(int id);
  List<T> getAll();
   void update(T entity,int id);
   void delete(int id);
} 
