package Utility;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import Models.Tweet;

public class Sentiment {
    public enum TypeSentiment {
        VERY_NEGATIVE(0), NEGATIVE(1), NEUTRAL(2), POSITIVE(3), VERY_POSITIVE(4);

        int index;
        private TypeSentiment(int index) {
            this.index = index;
        }

        public static TypeSentiment fromIndex(int index) {
            for (TypeSentiment typeSentiment: values()) {
                if (typeSentiment.index == index) {
                    return typeSentiment;
                }
            }

            return TypeSentiment.NEUTRAL;
        }
    }

    public List<Tweet> getTwitterAndAnalyzeSentiment(String text){
        HashMap< TypeSentiment, Integer > sentiments = new HashMap <Sentiment.TypeSentiment, Integer > ();
        Twitter twitter = new Twitter();
        List<Tweet> list = new ArrayList<Tweet>();
        try {
            list = twitter.getTodayTweet(text);
        }
        catch (Exception e){
            System.out.println("Nejsou dostupne zadne tweety");
        }

        for (Tweet status: list) {

            Sentiment.TypeSentiment sentiment = analyzeSentiment(status.getText());
            Integer value = (Integer)sentiments.get(sentiment);
            status.setSentiment(sentiment.toString());
        }
        return list;
    }
    public static Sentiment.TypeSentiment analyzeSentiment(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        int mainSentiment = 0;

        if (text != null && text.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(text);

            for (CoreMap sentence: annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }

            }
        }

        return Sentiment.TypeSentiment.fromIndex(mainSentiment);
    }
}
