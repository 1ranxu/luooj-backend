package com.luoying.judge.sandbox;

import com.luoying.judge.sandbox.impl.ExampleCodeSandBox;
import com.luoying.judge.sandbox.model.ExecuteCodeRequest;
import com.luoying.judge.sandbox.model.ExecuteCodeResponse;
import com.luoying.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeSandBoxTest {
    @Value("${codesandbox.type:example}")
    private String type;
    @Test
    void executeCode() {
        CodeSandBox codeSandBox = new ExampleCodeSandBox();
        List<String> inputList= Arrays.asList("1 2","3 4");
        String code = "int main(){}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        ExecuteCodeRequest codeRequest = ExecuteCodeRequest.builder()
                .inputList(inputList)
                .code(code)
                .language(language)
                .build();
        codeSandBox.executeCode(codeRequest);
    }

    @Test
    void executeCodeByValue() {
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        List<String> inputList= Arrays.asList("1 2","3 4");
        String code = "int main(){}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        ExecuteCodeRequest codeRequest = ExecuteCodeRequest.builder()
                .inputList(inputList)
                .code(code)
                .language(language)
                .build();
        codeSandBox.executeCode(codeRequest);
    }

    @Test
    void executeCodeByValue_Proxy() {
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        CodeSandBoxProxy codeSandBoxProxy = new CodeSandBoxProxy(codeSandBox);
        List<String> inputList= Arrays.asList("1 2","3 4");
        String code = "int main(){}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        ExecuteCodeRequest codeRequest = ExecuteCodeRequest.builder()
                .inputList(inputList)
                .code(code)
                .language(language)
                .build();
        codeSandBoxProxy.executeCode(codeRequest);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String type = sc.nextLine();
            CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
            List<String> inputList = Arrays.asList("1 2", "3 4");
            String code = "int main(){}";
            String language = QuestionSubmitLanguageEnum.JAVA.getValue();
            ExecuteCodeRequest codeRequest = ExecuteCodeRequest.builder()
                    .inputList(inputList)
                    .code(code)
                    .language(language)
                    .build();
            codeSandBox.executeCode(codeRequest);
        }
    }
}