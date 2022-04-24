import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;


    public class Main {

        enum TypeSentiment {
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


        public static TypeSentiment analyzeSentiment(String text) {
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

            return TypeSentiment.fromIndex(mainSentiment);
        }



        public static void main(String[] args) throws Exception {
           //String text = "tvoje mama je gej";
//
           //TypeSentiment sentiment = analyzeSentiment(text);
           //System.out.println(sentiment);

            HashMap < TypeSentiment, Integer > sentiments = new HashMap < Main.TypeSentiment, Integer > ();
            Twitter twitter = new Twitter();
            List < Tweet > list = twitter.getTodayTweet();

         for (Tweet status: list) {

             TypeSentiment sentiment = analyzeSentiment(status.getText());
             Integer value = sentiments.get(sentiment);
             System.out.println(sentiment +":"+status.getText());
             if (value == null) {
                 value = 0;
             }

             value++;

         }

         int size = list.size();
         System.out.println("Sentiments about Bitcoin on " + size + " tweets");

         for (Entry < TypeSentiment, Integer > entry: sentiments.entrySet()) {
             System.out.println(entry.getKey() + " => " + (entry.getValue() * 100) / size + " %");
         }


            BitcoinTwitterOkno frame = new BitcoinTwitterOkno("Bitcoin Sentiment");
		    frame.setVisible(true);

      }


    }
