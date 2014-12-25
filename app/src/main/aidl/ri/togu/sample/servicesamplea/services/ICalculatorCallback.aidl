// ICalculatorCallback.aidl
package ri.togu.sample.servicesamplea.services;

oneway interface ICalculatorCallback {
    void resultSum(int value);
}