package inf112.skeleton.app.objects.cards;

public class CardTranslator {

    public static NonTextureProgramCard intToProgramCard(int[] intCard){
        int value = intCard[0];
        boolean rotate = intToBoolean(intCard[1]);
        boolean rotateLeft = intToBoolean(intCard[2]);
        boolean rotateRight = intToBoolean(intCard[3]);
        int priority = intCard[4];
        return new NonTextureProgramCard(value, rotate, rotateLeft, rotateRight, priority);
    }
    public static int[] programCardsToInt(ProgramCard programCard){
        return new int[]{programCard.getValue(), booleanToInt(programCard.getRotate()),
                booleanToInt(programCard.getRotateLeft()), booleanToInt(programCard.getRotateRight()), programCard.getPriorityValue()};
    }

    public static boolean intToBoolean(int i){
        return i != 0;
    }

    public static int booleanToInt(boolean b){
        if(b){
            return 1;
        }else {
            return 0;
        }
    }
}
