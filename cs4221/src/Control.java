public class Control{

    public String[][] readInput(String input,int a,int b){

        String[][]fds = new String[a][b];
        int x = 0,y = 0;
        
        for (int i = 0; i < a ;i++){
            for (int j = 0; j < b; j++){
                fds[i][j] = "";
            }
        }

        char [] acceptableInputs = {'A','B','C','D','E',
                                    'F','G','H','I','J',
                                    'K','L','M','N','O',
                                    'P','Q','R','S','T',
                                    'U','V','W','X','Y','Z'};
        boolean inputStart = false;

        for (int i = 0; i < input.length(); i++){
             if(!inputStart && input.charAt(i) == '='){
                 inputStart = true;
             }

             if(inputStart){

                if(input.charAt(i) == '-'){
                        y++;
                }
                else if(input.charAt(i) == ','){
                    x++;
                    y=0;
                }
                else if(input.charAt(i) == '>'){
                }
                else{
                    for (int j = 0; j < acceptableInputs.length; j++){
                        if(input.charAt(i) == acceptableInputs[j]){
                            fds[x][y] = fds[x][y].concat(Character.toString(input.charAt(i)));
                            fds[x][y] = fds[x][y].concat(", ");
                        }
                    }
                }
            }
        }
        return fds;
    }

     public void printFD(String[][] array){
         int a = array.length,b = 2;

         for (int i = 0; i < a ;i++){
            System.out.println();
            if(array[i][0].isEmpty())break;
            for (int j = 0; j < b; j++){
                System.out.print(array[i][j]);
                if(j==0)System.out.print(" -> ");
            }
        }
     }

}
