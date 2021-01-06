clear all;
load allattributesaredouble16Class16Column.data;
X = allattributesaredouble16Class16Column;
TF1 = X(:,16)==1;
X(TF1,:) = [];
X = [X(:,1:15),X(:,17:26)];
allattributesaredouble16Class16Column = X;
X = zscore(X(:,16:25));
inputs = X.';
targets = allattributesaredouble16Class16Column(:,1:15).';

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

[c,cm] = confusion(targets,outputs);

fprintf('Percentage Correct Classification   : %f%%\n', 100*(1-c));
fprintf('Percentage Incorrect Classification : %f%%\n', 100*c);

% tInd = tr.testInd;
% tstOutputs = net(inputs(:,tInd));
% tstPerform = perform(net,targets(tInd),tstOutputs);

view(net);