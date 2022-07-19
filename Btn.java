import javax.swing.*;
//butonlardan oluşacağı için buton sınıfı yapıp jbuttona import ettim
public class Btn extends JButton {

    private int row,col,count; //satır,sütun ve sayaç ekledim bu sayaç etrafında kaç mayın var için lazım olacak
    private boolean mine,flag; //mayın ve bayrak boolean tanımlandı çünkü var mı diye soracağız

    public Btn(int row, int col){ //encapsulation
        this.row = row;
        this.col=col;
        this.count=0;
        this.mine=false;
        this.flag=false;
    }
        public int getRow(){

        return row;
        }
        public void setRow(int row){

        this.row=row;
        }
        public int getCol(){
            return col;
        }
        public void setCol(int col){
            this.col=col;
        }
        public int getCount(){
            return count;
        }
        public void setCount(int count){
            this.count=count;
        }
        public boolean isMine(){
            return mine;
        }
        public void setMine(boolean mine){
            this.mine=mine;
        }
        public boolean isFlag(){
            return flag;
        }
        public void setFlag(boolean flag){
            this.flag=flag;
        }

}
