// ICalculatorService.aidl
package ri.togu.sample.servicesamplea.services;

import ri.togu.sample.servicesamplea.services.ICalculatorCallback;

interface ICalculatorService {
    oneway void registerCallback(ICalculatorCallback callback);
    oneway void unregisterCallback(ICalculatorCallback callback);
    int add(int lhs, int rhs);
}

// 「oneway」: メソッドの終了を待つ必要がない
// 「in」:入力にのみ使用、「out」:出力にのみ使用、「inout」:入出力に使用
/*
package <PackageName>;
[import <FQCN>;]
[parcelable <FQCN>;]

[oneway] interface <InterfaceName> {
    [oneway]<ReturnType> <MethodName> ([in|out|inout] <ArgType> <ArgName> ……);
}
*/