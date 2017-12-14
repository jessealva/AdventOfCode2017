package com.adventofcode.jessealva;

/**
 * Created by jesusalva on 12/1/17.
 */
public class Day1 {
    private static final String capP11 = "1122";
    private static final String capP12 = "1111";
    private static final String capP13 = "1234";
    private static final String capP14 = "91212129";
    private static final String capP21 = "1212";
    private static final String capP22 = "1221";
    private static final String capP23 = "123425";
    private static final String capP24 = "123123";
    private static final String capP25 = "12131415";
    private static final String capFinal = "516299281491169512719425276194596424291268712697155863651846937925928456958813624428156218468331423858422613471962165756423837756856519754524985759763747559711257977361228357678293572698839754444752898835313399815748562519958329927911861654784216355489319995566297499836295985943899373615223375271231128914745273184498915241488393761676799914385265459983923743146555465177886491979962465918888396664233693243983969412682561799628789569294374554575677368219724142536789649121758582991345537639888858113763738518511184439854223386868764189133964543721941169786274781775658991329331759679943342217578532643519615296424396487669451453728113114748217177826874953466435436129165295379157226345786756899935747336785161745487933721527239394118721517195849186676814232887413175587327214144876898248571248517121796766248817366614333915154796983612174281237846165129114988453188844745119798643314857871527757831265298846833327863781341559381238458322786192379487455671563757123534253463563421716138641611915686247343417126655317378639314168461345613427262786624689498485599942336813995725145169355942616672812792174556866436158375938988738721253664772584577384558696477546232189312287262439452141564522329987139692281984783513691857538335537553448919819545332125483128878925492334361562192621672993868479566688564752226111784486619789588318171745995253645886833872665447241245329935643883892447524286642296955354249478815116517315832179925494818748478164317669471654464867111924676961162162841232473474394739793968624974397916495667233337397241933765513777241916359166994384923869741468174653353541147616645393917694581811193977311981752554551499629219873391493426883886536219455848354426461562995284162323961773644581815633779762634745339565196798724847722781666948626231631632144371873154872575615636322965353254642186897127423352618879431499138418872356116624818733232445649188793318829748789349813295218673497291134164395739665667255443366383299669973689528188264386373591424149784473698487315316676637165317972648916116755224598519934598889627918883283534261513179931798591959489372165295";

    public static void main(String[] args) {

        System.out.println("Captcha of " + capP11 + " is " + getCaptchaP1(getArrFromStr(capP11)) + " but should be 3");
        System.out.println("Captcha of " + capP12 + " is " + getCaptchaP1(getArrFromStr(capP12)) + " but should be 4");
        System.out.println("Captcha of " + capP13 + " is " + getCaptchaP1(getArrFromStr(capP13)) + " but should be 0");
        System.out.println("Captcha of " + capP14 + " is " + getCaptchaP1(getArrFromStr(capP14)) + " but should be 9");
        System.out.println("Captcha of " + capFinal + " is " + getCaptchaP1(getArrFromStr(capFinal)));

        System.out.println("Captcha of " + capP21 + " is " + getCaptchaP2(getArrFromStr(capP21)) + " but should be 6");
        System.out.println("Captcha of " + capP22 + " is " + getCaptchaP2(getArrFromStr(capP22)) + " but should be 0");
        System.out.println("Captcha of " + capP23 + " is " + getCaptchaP2(getArrFromStr(capP23)) + " but should be 4");
        System.out.println("Captcha of " + capP24 + " is " + getCaptchaP2(getArrFromStr(capP24)) + " but should be 12");
        System.out.println("Captcha of " + capP25 + " is " + getCaptchaP2(getArrFromStr(capP25)) + " but should be 4");
        System.out.println("Captcha of " + capFinal + " is " + getCaptchaP2(getArrFromStr(capFinal)));
    }

    public static int[] getArrFromStr(String str) {
        byte[] bytes = str.getBytes();
        int[] digits = new int[bytes.length];
        for (int i = 0;i < digits.length; ++i) {
            digits[i] = bytes[i] - 48; //48 is the ascii offset...ascii value vs dec value.
        }

        return digits;
    }

    public static void print(int[] array) {
        if (array != null) {
            for (int i = 0 ; i < array.length; ++i) {
                System.out.print(array[i] + " ");
            }
            System.out.println("");
        }
    }

    public static int getCaptchaP1(int[] digits) {
        print(digits);
        int sum = 0;
        for(int i = 0 ;i < digits.length; ++i) {
            int next = i == (digits.length-1) ? 0 : i+1;
            if (digits[i] == digits[next]) {
                sum += digits[i];
            }
        }
        return sum;
    }

    public static int getCaptchaP2(int[] digits) {
        //print(digits);
        int sum = 0;
        int offset = digits.length/2;
//        System.out.println(offset);
        for(int i = 0 ;i < digits.length; ++i) {
            int next = (i+offset)%digits.length;
//            System.out.println("Comparing digits[" + i + "] to digits[" + next + "]");
            if (digits[i] == digits[next]) {
                sum += digits[i];
            }
        }
        return sum;
    }
}
