package utils

case class TxSuccessAction[A](txResult: A, successAction: A => _) {

  def execute = successAction(txResult)

}
