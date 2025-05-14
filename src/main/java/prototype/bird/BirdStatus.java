package prototype.bird;

import java.util.List;
import java.util.Random;

public enum BirdStatus {
    
    EGG("알 상태",
            List.of("제비가 둥지를 틀었네요!\n좋은 일이 있을 거에요.",
                    "조시유명(鳥始有鳴):\n좋은 일의 징조나 시작\n뭐든 시작이 중요한 법이죠.",
                    "까치가 우는 것처럼\n근사한 하루의 시작을 얼리버드와 함께!"
            )),
    INFANCY("유아기",
            List.of("학수고대(鶴首苦待):\n간절한 기다림\n때로는 웅크리고 기다리는 것도 필요하죠.",
                    "병아리가 울면 닭도 뒤를 돌아본답니다.\n때로는 약자의 입장에 있더라도\n목소리를 내야할 때가 있는 법.",
                    "봉황지지(鳳凰之志):\n봉황과 같이 높은 뜻을 품음\n끊임없이 나아가다보면 목표에 도달할 거에요."
            )),
    MATURITY("성숙기",
            List.of("비조장지(飛鳥長枝):\n인재는 좋은 자리를 택함\n나아감에 있어서 때로는 위치도 중요합니다.",
                    "독수리는 닭장에 살지 않는다는 말처럼\n좁은 틀에 갇히지 말도록 조심하세요.",
                    "새도 보금자리는 필요하답니다.\n가끔은 쉬면서 에너지를 채우는 것도 필요할지 몰라요."
            )),
    ADULT("성체",
            List.of("봉비작작(鳳飛綽綽):\n봉황이 나는 듯, 우아하고 귀한 모습.\n얼리버드와 함께 더욱 고귀해졌을 당신을 응원합니다!",
                    "학이 저 하늘을 향해 날듯이 솟는 것처럼\n당신의 앞길에도 좋은 성과들이 가득할 겁니다.",
                    "태평성대에만 나타난다는 봉황,\n이젠 당신이 그 봉황이 된 걸지도요?"
            ));
    
    private final String growStage;
    private final List<String> messages;
    
    BirdStatus(String growStage, List<String> messages) {
        this.growStage = growStage;
        this.messages = messages;
    }
    
    public String getGrowStage() {
        return growStage;
    }
    
    public List<String> getRandomMessages(int count) {
        Random random = new Random();
        return random.ints(0, messages.size())
                .distinct()
                .limit(count)
                .mapToObj(messages::get)
                .toList();
        
    }
}
