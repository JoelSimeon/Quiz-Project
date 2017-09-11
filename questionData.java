/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickquiz;

/**
 *
 * @author Joel
 */
class QuestionData {

        String QuestionNumber = new String();
        String Topic = new String();
        String Question = new String();
        String AnswerA = new String();
        String AnswerB = new String();
        String AnswerC = new String();
        String AnswerD = new String();
        String CorrectAnswer = new String();

    
    public QuestionData(String questionnumber, String topic, String question, String answera, String answerb, String answerc, String answerd, String correctanswer)  {
        
        QuestionNumber = questionnumber;
        Topic = topic;
        Question = question;
        AnswerA = answera;
        AnswerB = answerb;
        AnswerC = answerc;
        AnswerD = answerd;
        CorrectAnswer = correctanswer;
    }
    
    public String getQuestionNumber() 
    {
        return QuestionNumber;
    }
    
    public String getTopic()
    {
        return Topic;
    }
    
    public String getQuestion()
    {
        return Question;
    }
    
    public String getAnswerA()
    {
        return AnswerA;
    }
    
    public String getAnswerB() 
    {
        return AnswerB;
    }
    
    public String getAnswerC()
    {
        return AnswerC;
    }
    
    public String getAnswerD()
    {
        return AnswerD;
    }
            
    public String getCorrectAnswer()
    {
        return CorrectAnswer;
    }
    
}
