clear all;
load allattributesaredoubleTwoClassTwoColumn.data;
X = allattributesaredoubleTwoClassTwoColumn;
X = zscore(X(:,1:10));
inputs = X(:,1:10).';
targets = allattributesaredoubleTwoClassTwoColumn(:,11:12).';

% load house_dataset;
% [inputs,targets] = house_dataset;

% load cancer_dataset;
% [inputs,targets] = cancer_dataset;

hiddenLayerSize = 10;
net = patternnet(hiddenLayerSize);

net.divideParam.trainRatio = 70/100;
net.divideParam.valRatio = 15/100;
net.divideParam.testRatio = 15/100;

[net,tr] = train(net,inputs,targets);

outputs = net(inputs);
errors = gsubtract(targets,outputs);
performance = perform(net,targets,outputs);

% tInd = tr.testInd;
% tstOutputs = net(inputs(:,tInd));
% tstPerform = perform(net,targets(tInd),tstOutputs);

view(net);