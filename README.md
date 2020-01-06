# cocangua

The controller process the user requests.
Based on the user request, the Controller calls methods in the View and
Model to accomplish the requested action. 

Note: The view should not send to the model but it is often useful
for the view to receive update event information from the model. 
However you should not update the model from the view.