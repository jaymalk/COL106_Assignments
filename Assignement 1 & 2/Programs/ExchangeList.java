public class ExchangeList {
    public MyLinkedList list = new MyLinkedList();

    public void addExchange(Exchange e) {
        list.Insert(e);
    }

    public void removeChild(Exchange e) {
        list.deleteMember(e);
    }
}
